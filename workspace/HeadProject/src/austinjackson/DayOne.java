package austinjackson;
import javax.swing.JOptionPane;

public class DayOne
{
	public static String output  = "";

	public static byte b = 127;
	public static short s = 24;
	public static int i = 892;
	public static long l = 1283921983;
	public static float f = 124.2f;
	public static double d = 12321.21321;
	public static char c = 'A';
	public static boolean bool = false;
	public static String str = "Words!";
	
	public static void main ( String[] args )
	{
		
//		JFrame frame = new JFrame("frame");
//		frame.add(new JTextArea());
//		frame.setVisible(true);
		
		String n = "\n";

		output = ""+
		"/////////////////////////////////"+n+
		"*Austin Jackson                  *"+n+
		"*                                *"+n+
		"*        integer types           *"+n+
		"*                                *"+n+
		"*8 bit - byteOne = "+b+"           *"+n+
		"*16 bit - shortOne = "+s+"          *"+n+
		"*32 bit - intOne = "+i+"           *"+n+
		"*64 bit - longOne = "+l+"   *"+n+
		"*                                *"+n+
		"*         real types             *"+n+
		"*                                *"+n+
		"*32 bit - floatOne = "+f+"       *"+n+
		"*64 bit - doubleOne = "+d+"*"+n+
		"*                                *"+n+
		"*16 bit - charOne = "+c+"            *"+n+
		"*                                *"+n+
		"*         other types            *"+n+
		"*                                *"+n+
		"*booleanOne = "+bool+"              *"+n+
		"*stringOne = "+str+"              *"+n+
		"/////////////////////////////////"+n;
		
		System.out.println(output);
		JOptionPane.showMessageDialog(null, output);
		
	}
	
}
