package web.printer;

import web.struct.Destination;

public class VersionPrinter implements Printer {

    @Override
    public void print(Destination destination) {
        System.out.printf("  ** Version: %s%n", destination.fetch().get(0));
    }

}
