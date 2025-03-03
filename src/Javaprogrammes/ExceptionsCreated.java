package Javaprogrammes;

public class ExceptionsCreated {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a=25;
		double b=25.25;
		String s="Suraj";
		int number=84482;
		String rd=null;
		Double d=new Double(a);
		try {
			//Integer c=new Integer(b);
			//String convertStringednumber =Integer.toString(number);
			//System.out.println(convertStringednumber);

			//null pointer exception
			rd.charAt(0);

			//number format exception
			int g=Integer.parseInt(s);
			System.out.println(g);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}

}
