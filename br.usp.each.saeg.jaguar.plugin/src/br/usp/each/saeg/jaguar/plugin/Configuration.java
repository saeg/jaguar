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
    
    public static final boolean ROADMAP = true; //chooses between Roadmap or CodeHierarchy
    public static final boolean EXPERIMENT_VERSION = false; //include actions to guide experiments with users   
    public static final boolean EXPERIMENT_JAGUAR_FIRST = true; //experiment starts from jaguar to eclipse, or from eclipse to jaguar - two bugs per experiment
    public static final boolean SEND_DATA = false;//put a valid user, host and password in the ScpSend class to execute this option 
    
}
