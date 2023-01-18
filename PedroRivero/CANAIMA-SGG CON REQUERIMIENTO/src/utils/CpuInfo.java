/*
 * Copyright (c) 2006-2008 Hyperic, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package utils;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.SigarCommandBase;

/**
 * Display cpu information.
 */
public class CpuInfo extends SigarCommandBase {

    public CpuInfo() {
        super();
    }

    public StringBuilder getCpuInfo() throws Exception {
        org.hyperic.sigar.CpuInfo info = this.sigar.getCpuInfoList()[0];

        StringBuilder aux = new StringBuilder(info.getVendor()).append(info.getModel()).append(info.getMhz()).append(info.getTotalCores());

        if ((info.getTotalCores() != info.getTotalSockets()) || (info.getCoresPerSocket() > info.getTotalCores())) {
            aux.append(info.getTotalSockets()).append(info.getCoresPerSocket());
        }

        long cacheSize = info.getCacheSize();
        if (cacheSize != Sigar.FIELD_NOTIMPL) {
            aux.append(cacheSize);
        }

        final byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();

        if (mac != null) {
            for (int i = 0; i < mac.length; i++) {
                aux.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
        }

        return aux;
    }

    @Override
    public void output(String[] _args) throws SigarException {
    }

    public static void main(String[] args) {
        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();

        /* For each filesystem root, print some info */
        for (File root : roots) {
            System.out.println("File system root: " + root.getAbsolutePath());
            System.out.println("Total space (bytes): " + root.getTotalSpace());
            System.out.println("Free space (bytes): " + root.getFreeSpace());
            System.out.println("Usable space (bytes): " + root.getUsableSpace());
        }
    }
}
