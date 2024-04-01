package com.isiweek;

import com.isiweek.auth.LoginData;
import com.isiweek.auth.SignupData;
import com.isiweek.auth.ValidSignupData;
import com.isiweek.debtor.Debtor;
import com.isiweek.debtor.DebtorRepository;
import com.isiweek.lender.Lender;
import com.isiweek.lender.LenderRepository;
import com.isiweek.role.Role;
import com.isiweek.role.RoleRepository;
import com.isiweek.security.AuthServiceImpl;
import com.isiweek.security.AuthenticationUser;
import com.isiweek.security.JwtAuthResponse;
import com.isiweek.security.JwtTokenProvider;
import com.isiweek.security.RoleNotFoundException;
import com.isiweek.security.StatusNotFoundException;
import com.isiweek.security.UserAlreadyExistsException;
import com.isiweek.status.Status;
import com.isiweek.status.StatusRepository;
import com.isiweek.user.User;
import com.isiweek.user.UserService;
import com.isiweek.util.CustomCollectors;
import com.isiweek.util.WebUtils;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Validated
@Log4j2
@RequestMapping("/")
@Controller
public class HomeController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthServiceImpl authServiceImpl;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final LenderRepository lenderRepository;
    private final DebtorRepository debtorRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public HomeController(JwtTokenProvider inJwtTokenProvider, AuthServiceImpl inAuthServiceImpl, final UserService userService, final RoleRepository roleRepository,
            final LenderRepository lenderRepository, final DebtorRepository debtorRepository,
            final StatusRepository statusRepository) {

        this.jwtTokenProvider = inJwtTokenProvider;
        this.authServiceImpl = inAuthServiceImpl;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.lenderRepository = lenderRepository;
        this.debtorRepository = debtorRepository;
        this.statusRepository = statusRepository;
    }

    @GetMapping({"/", "/index", "/index.html", "/home", "/home.html"})
    public String index() {
        return "public/index";
    }

    @GetMapping({"/signup", "/public/isiweek-signup"})
    public String GetMappingSignUp(@ModelAttribute("data") final SignupData signupData) {
        log.info("GetMappingSignUp Inmediatamente al entrar");

        signupData.reset();
        return "public/isiweek-signup";
    }

    @PostMapping({"/signup", "/public/isiweek-signup"})
    public ModelAndView PostMappingSignUp(@ModelAttribute("data") @Valid @ValidSignupData final SignupData signupData,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes,
            final ModelAndView modelAndView) {
        log.info("PostMappingSignUp inmediatamente al entrar");

        if (bindingResult.hasErrors()) {
            return new ModelAndView("public/isiweek-signup", "data", signupData);
        }

        log.info("PostMappingSignUp antes de tratar de hacer signup");

        try {
            log.info(signupData);
            Optional<User> user = authServiceImpl.signup(signupData);
            signupData.reset();
            redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
            modelAndView.clear();
            log.info("PostMappingSignUp despues hacer signup");
            return new ModelAndView("redirect:/");

        } catch (RoleNotFoundException ex) {
            bindingResult.rejectValue("role", "error.role.notfound", "Role not found");
            return new ModelAndView("public/isiweek-signup", "data", signupData);
        } catch (StatusNotFoundException ex) {
            bindingResult.rejectValue("status", "error.status.notfound", "Status not found");
            return new ModelAndView("public/isiweek-signup", "data", signupData);
        } catch (UserAlreadyExistsException ex) {
            log.error("Error, usuario ya existe");
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("user.create.alreadyexist"));
            bindingResult.rejectValue("email", "error.username.alreadyexist", "User already exist");
            log.error("retornando a: /signup");
            return new ModelAndView("public/isiweek-signup", "data", signupData);
        } catch (Exception ex) {
            bindingResult.rejectValue(ex.toString(), "error.general");
            return new ModelAndView("public/isiweek-signup", "data", signupData);
        }
    }

    @GetMapping({"/login", "/public/isiweek-login"})
    public String GetMappingLogin(@ModelAttribute("data") final LoginData loginData) {

        return "public/login";
    }

    @PostMapping({"/login", "/public/isiweek-login"})
    public String PostMappingLogin(@RequestBody LoginData loginData) {

        final Optional<User> userOp = authServiceImpl.login(loginData);

        if (userOp.isPresent()) {

            // User is authenticated
            final AuthenticationUser authUser = new AuthenticationUser(
                    userOp.get().getEmail(),
                    userOp.get().getPassword(),
                    true, (List<Role>) userOp.get().getRoles());

            // Generate JWT token
            final String jwtToken = jwtTokenProvider.generateToken(authUser);

            // Build JWT authentication response
            JwtAuthResponse jwtAuthResponse = JwtAuthResponse.builder()
                    .accessToken(jwtToken)
                    .expiresIn(jwtTokenProvider.getJwtExpirationDate())
                    .build();

            // Return successful response with JWT token
            return null;
        }

        // User not found, return UNAUTHORIZED response
        return null;
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleValidationException(ConstraintViolationException ex, Model model) {
//
//        if (model != null) {
//            model.addAttribute("title", "Passwords do not match");
//            model.addAttribute("status", "Passwords do not match");
//
//            String errorMessage = "Passwords do not match";
//            if (ex != null) {
//                errorMessage += ":\n" + ex.toString();
//            }
//
//            model.addAttribute("errorMessage", errorMessage);
//        }
//
//        return "public/isiweek-error";
//    }
    @ModelAttribute
    public void prepareContext(final Model model) {

        model.addAttribute("idRoleValues", roleRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Role::getId, Role::getName)));

        model.addAttribute("idLenderValues", lenderRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Lender::getId, Lender::getObservations)));

        model.addAttribute("idDebtorValues", debtorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Debtor::getId, Debtor::getName)));

        model.addAttribute("idStatusValues", statusRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Status::getId, Status::getId)));
    }

}
