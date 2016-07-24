package br.usp.each.saeg.jaguar.plugin;

public class Configuration {
	
	public static enum SizingAttribute {
        PACKAGE, CLASS;
    }

    public static final float SCORE_THRESHOLD = .03f;
    public static boolean CONSIDER_LINE_OCURRENCES = true;
    //public static final SizingAttribute SIZING_ATTRIBUTE = SizingAttribute.CLASS;
    //public static TreeDataComparator TREE_DATA_COMPARE_STRATEGY = TreeDataComparator.SCORE_AND_NUMBER_AND_METHODS;
    public static final boolean RENDER_WIREFRAME = false;
    
    public static final boolean ROADMAP = false; //chooses Roadmap
    public static final boolean CODEHIERARCHY = false; //chooses CodeHierarchy
    public static final boolean ONLY_LINE_DUA_LEVEL = false; //shows only the lowest level, lines or duas
    public static final boolean ONLY_METHOD_LEVEL = true; //shows only methods
    public static final boolean EXPERIMENT_VERSION = false; //include actions to guide experiments with users   
    public static final boolean EXPERIMENT_JAGUAR_FIRST = true; //experiment starts from jaguar to eclipse, or from eclipse to jaguar - two bugs per experiment
    public static final boolean SEND_DATA = false;//put a valid user, host and password in the ScpSend class to execute this option 
    
}
