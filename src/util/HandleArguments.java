package util;

import java.util.List;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class HandleArguments {
    /**
     * Responsible for processing/error checking arguments given according to requirements
     * Will only accept one argument and it has to be the filename containing
     * list of accounts
     *
     * @param args: List of String arguments
     * @return filename
     */
    public static String process(List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Will only take one argument as a filename");
        }

        return args.get(0);
    }
}
