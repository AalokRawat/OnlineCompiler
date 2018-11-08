package my.compiler;/*
 * Project Horizon
 * (c) 2018-2019 VMware, Inc. All rights reserved.
 * VMware Confidential.
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class Compiler {

    @RequestMapping(value = "/compiler/java", method = RequestMethod.POST)
    public String java( @RequestParam String editor){
        File file = new File("Main.java");
        FileOutputStream outputStream;
        String result="";
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(editor.getBytes());
            String compile_cmd = "javac Main.java";
            Process proc =Runtime.getRuntime().exec( compile_cmd );
            proc.waitFor();
            String run_cmd="java Main";
            proc = Runtime.getRuntime().exec( run_cmd );
            proc.waitFor();
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                result+=s;
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
                result+=s;
            }

            String delete_cmd = "rm Main.class";
            Runtime.getRuntime().exec(delete_cmd);
            file.delete();
        } catch (FileNotFoundException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
