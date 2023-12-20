package com.compilador;

/**
 *
 */
class Token {

    private final TokenType type;
    private final String value;
    private final int nlinea;

    public Token(TokenType type, String value, int nlinea) {
        this.type = type;
        this.value = value;
        this.nlinea = nlinea;
    }

    @Override
    public String toString() {
        return "Token{"
                + "type=" + getType()
                + ", value='" + getValue() + '\''
                + '}';
    }

    /**
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the nlinea
     */
    public int getNlinea() {
        return nlinea;
    }
}
