
public class Templates {
    private static String[] gliderTemplates = {"Glider","Spaceship","Coeship"};
    private static String[] oscillatorTemplates = {"Pulsar","Penta-decathlon","Tumbler"};
    private static String[] otherTemplates = {"Acorn", "Glider Gun"};

    public static void loadTemplate(String templateName, Grid grid) {
        Boolean[][] template = new Boolean[0][0];
        Boolean center = false;

        switch(templateName){
            // Glider Templates
            case "Glider":
                center = false;
                template = new Boolean[][]{ {false, false,  true},
                                            {true,  false,  true},
                                            {false, true,   true},
                };
                break;
            case "Spaceship":
                center = false;
                template = new Boolean[][]{ {false, true,   true,   true,   true},
                                            {true,  false,  false,  false,  true},
                                            {false, false,  false,  false,  true},
                                            {true,  false,  false,  true,   false}
                };
                break;
            case "Coeship":
                center = false;
                template = new Boolean[][]{ {false, false, false, false, true , true , true , true , true , true },
                                            {false, false, true , true , false, false, false, false, false, true },
                                            {true , true , false, true , false, false, false, false, false, true },
                                            {false, false, false, false, true , false, false, false, true , false},
                                            {false, false, false, false, false, false, true , false, false, false},
                                            {false, false, false, false, false, false, true , true , false, false},
                                            {false, false, false, false, false, true , true , true , true , false},
                                            {false, false, false, false, false, true , true , false, true , true },
                                            {false, false, false, false, false, false, false, true , true , false},
                };
                break;

            // Oscillator Templates
            case "Pulsar":
                center = true;
                template = new Boolean[][]{ {false, false, true , true , true , false, false, false, true , true , true , false, false},
                                            {false, false, false, false, false, false, false, false, false, false, false, false, false},
                                            {true , false, false, false, false, true , false, true , false, false, false, false, true },
                                            {true , false, false, false, false, true , false, true , false, false, false, false, true },
                                            {true , false, false, false, false, true , false, true , false, false, false, false, true },
                                            {false, false, true , true , true , false, false, false, true , true , true , false, false},
                                            {false, false, false, false, false, false, false, false, false, false, false, false, false},
                                            {false, false, true , true , true , false, false, false, true , true , true , false, false},
                                            {true , false, false, false, false, true , false, true , false, false, false, false, true },
                                            {true , false, false, false, false, true , false, true , false, false, false, false, true },
                                            {true , false, false, false, false, true , false, true , false, false, false, false, true },
                                            {false, false, false, false, false, false, false, false, false, false, false, false, false},
                                            {false, false, true , true , true , false, false, false, true , true , true , false, false}
                };
                break;
            case "Penta-decathlon":
                center = true;
                template = new Boolean[][]{ {false, false, true , false, false, false, false, true , false, false},
                                            {true , true , false, true , true , true , true , false, true , true },
                                            {false, false, true , false, false, false, false, true , false, false}
                };
                break;
            case "Tumbler":
                center = true;
                template = new Boolean[][]{ {false, true , false, false, false, false, false, true , false},
                                            {true , false, true , false, false, false, true , false, true },
                                            {true , false, false, true , false, true , false, false, true },
                                            {false, false, true , false, false, false, true , false, false},
                                            {false, false, true , true , false, true , true , false, false},
                };
                break;

            // Other Templates
            case "Glider Gun":
                center = false;
                template = new Boolean[][]{ {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true , false, false, false, false, false, false, false, false, false, false, false},
                                            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true , false, true , false, false, false, false, false, false, false, false, false, false, false},
                                            {false, false, false, false, false, false, false, false, false, false, false, false, true , true , false, false, false, false, false, false, true , true , false, false, false, false, false, false, false, false, false, false, false, false, true , true },
                                            {false, false, false, false, false, false, false, false, false, false, false, true , false, false, false, true , false, false, false, false, true , true , false, false, false, false, false, false, false, false, false, false, false, false, true , true },
                                            {true , true , false, false, false, false, false, false, false, false, true , false, false, false, false, false, true , false, false, false, true , true , false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                                            {true , true , false, false, false, false, false, false, false, false, true , false, false, false, true , false, true , true , false, false, false, false, true , false, true , false, false, false, false, false, false, false, false, false, false, false},
                                            {false, false, false, false, false, false, false, false, false, false, true , false, false, false, false, false, true , false, false, false, false, false, false, false, true , false, false, false, false, false, false, false, false, false, false, false},
                                            {false, false, false, false, false, false, false, false, false, false, false, true , false, false, false, true , false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                                            {false, false, false, false, false, false, false, false, false, false, false, false, true , true , false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                };
                break;
            case "Acorn":
                center = true;
                template = new Boolean[][]{ {false , true , false, false, false, false, false},
                                            {false , false, false, true , false, false, false},
                                            {true  , true , false, false, true , true , true },
                };
                break;
        }
        grid.loadArray(center,template);
    }

    public static String[] getGlider() { return gliderTemplates;}
    public static String[] getOscillator() { return oscillatorTemplates;}
    public static String[] getOther() { return otherTemplates;}
}
