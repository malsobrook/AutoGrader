public class testFail {
	public static void testFail(String[] args)
		{
			//bad level of indents
	Scanner scan = new Scanner(System.in);
	            for(int i=12; i > 2; i--)
	            {
	            	        int i = scan.nextInt();
	            	}
	    // call a method that doesnt exist
	    fakeMethodCall(argument);
	    scan.close();
	}
		}

// Auto generated comment block NOT at top of file
// anticipated values are
// ~50% on indentation consistency
// ~70% on indentation correctness
// ~70% on bracket consistency
// 100% on bracket correctness
// No: import at top
// No: Comment at top

import java.util.Scanner;