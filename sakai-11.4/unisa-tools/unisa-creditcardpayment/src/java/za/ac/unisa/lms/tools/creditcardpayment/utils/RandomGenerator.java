package za.ac.unisa.lms.tools.creditcardpayment.utils;

public class RandomGenerator {

	private int randseed1 = 1265437;
	private int randseed2 = 16532754;
	private String key = "946284625407581341";
	
	
	private void setRandKey(String key){
		
		randseed1 = Integer.parseInt(key.substring(0,9));
		randseed2 = Integer.parseInt(key.substring(key.length()-9,key.length()));
		
		int lower = (key.length()/2-2);
		int limit = Integer.parseInt(key.substring(lower-1,lower + 3));
		//System.out.println("lower = " + lower);
		//System.out.println("limit = "+limit);
		for (int i = 0; i < limit+1; i++) {
			rand();
		}
	}
	
	private int rand(){
		
		int z;
		int k;
		
		k = randseed1/ 53668;
		//System.out.println("k = "+k);
		randseed1 = 40014 * (randseed1 - k * 53668) - k * 12211;
		//System.out.println("randseed1 = "+randseed1);
		if (randseed1 < 0){
			randseed1 = randseed1 + 2147483563;
		}

		k = randseed2 / 52774;
		//System.out.println("k = "+k);
		randseed2 = 40692 * (randseed2 - k * 52774) - k * 3791;
		if (randseed2 < 0){
			randseed2 = randseed2 + 2147483399;
		}

		z = randseed1 - randseed2;
		if (z < 1){
			z = z + 2147483562;
		}

		//System.out.println("z = "+z);
		return z;
		
	}
	
	public String encrypt(String ccn){
	int j,ii,r,pR;
	String s ="";
	
	setRandKey(key);
	System.out.println("secret key = "+key);
	for (int i = 0; i < ccn.length(); i++) {
		j = Integer.parseInt(ccn.substring(i,i+1));
		//System.out.println("j = "+j);
		//System.out.println("i = "+i);
		ii = 0;
		pR = 0;
		r = rand();
		//System.out.println("rand = "+r);
		//System.out.println("r mod = "+r % 10);
		while ((r % 10)!=j){
			ii = ii+1;
			pR = r;
			r = rand();
		}
		pR = pR % 100;
		while (pR > 0){
			rand();
			pR = pR-1;
		}
		//System.out.println("chr = "+(char)(32+ii));
		s = s + (char)(32+ii);
		//System.out.println("s = "+s);
	}
	return s;
}

	public String decrypt(String eCcn){
	int j,r,pR;
	String s ="";
	setRandKey(key);
	for (int i = 0; i < eCcn.length(); i++) {
		//System.out.println("substr = " + eCcn.charAt(i));
		j = (int)(eCcn.charAt(i))-32;
		//System.out.println("j = " + j);
		pR = 0;
		r = rand();
		//System.out.println("rand = " + r);
		for (int ii = 0; ii < j; ii++) {
			pR = r;
			r = rand();
			//System.out.println("ii = " + ii);
	}

		//System.out.println("rand = " + r);
		//System.out.println("r mod = " + r % 10);
		s = s + String.valueOf(r % 10);
		//System.out.println("s = " + s);
		pR = pR % 100;
		while(pR>0){
			rand();
			pR = pR-1;
		}
	}
	return s;
	
}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RandomGenerator rGen = new RandomGenerator();
		
		System.out.println(rGen.randseed1);
		System.out.println(rGen.randseed2);
		
		int r;
		for (int i = 0; i < 1000; i++) {
			r = rGen.rand();
		}
	
	System.out.println("After 1000 numbers, RAND must return 197387928.");
	System.out.println("It returns                           "+ rGen.rand());

	//Now we can set it up with the secret key...
	rGen.setRandKey(rGen.key);

	//And here is the first 100 numbers:
	for (int i = 0; i < 10; i++) {
		for (int j = 0; j < 10; j++) {
			String s = "             " + rGen.rand();
			System.out.print(s.substring(s.length()-12, s.length()));
		}
		System.out.println();
	}


//	randomize
//	SecretKey=Int(rnd*1000000000) & Int(rnd*1000000000)

	String ccn = "454245671234090";
	System.out.println("CardNumber:" + ccn);

	String s = rGen.encrypt(ccn);
	System.out.println("Encrypted: " + s);

	String ss = rGen.decrypt(s);
	System.out.println("UnEnc:     " + ss);
	//---------------------------------------------------------
	ccn = "5470445004014020";
	System.out.println("CardNumber:" + ccn);

	 s = rGen.encrypt(ccn);
	System.out.println("Encrypted: " + s);

	 ss = rGen.decrypt(s);
	System.out.println("UnEnc:     " + ss);
	

	}

}
