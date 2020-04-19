abstract class SF
{
  protected abstract int score(int i, int k);
}

public class dna extends SF
{
  protected int W; //gap penalty
  protected String A; //string A input
  protected String B; //string B input
  protected String nA, nB, lines; //new string and lines
  protected byte[][] TB; //traceback matrix
  protected int[][] M; //score matrix
  int a, b, c;

  public dna(String a, String b)
  {
    A = "."+a; //A string with dummy char
    B = "."+b; //B string w dummy char
    nA = "";
    nB = "";
    lines = "";
    M = new int[A.length()][B.length()]; //score matrix of AxB size
    TB = new byte[A.length()][B.length()];
  }

  protected void initialize()
  {
    for(int i=1;i<A.length();i++)
    {
      M[i][0] = W + M[i-1][0];
      TB[i][0] = 1;
    }
    for(int k=1;k<B.length();k++)
    {
      M[0][k] = W + M[0][k-1];
      TB[0][k] = 2;
    }
  }

  protected int score(int i, int k)
  {
    if (A.charAt(i)==B.charAt(k)) { W = 0; return M[i][k]=1; }
    else { W = 0; return M[i][k]=0; }
  }

  //2. fill the matrix and calculate final score
  protected void fill()
  {
    //int a, b, c;
    a = b = c = 0x80000000;

    //fills matrix
    for(int i=1;i<A.length();i++)
    {
      for(int k=1;k<B.length();k++)
      {
        if (i<=0 && k<=0) { M[i][k] = 0; } // base case
        if (i>0 && k>0) { a = M[i-1][k-1] + score(i,k); } //diagonal value
        if (i>0) { b = M[i-1][k] + W; } //above value
        if (k>0) { c = M[i][k-1] + W; } //left value

        //fills with max value in M[i][k]
        if(Math.max(a,Math.max(b,c))== a) { M[i][k] = a; TB[i][k]=1; }
        if(Math.max(a,Math.max(b,c))== b) { M[i][k] = b; TB[i][k]=2; }
        if(Math.max(a,Math.max(b,c))== c) { M[i][k] = c; TB[i][k]=3; }
      }//k
    }//i
  }//fill()

  protected int alignscore()
  {
    return M[A.length()-1][B.length()-1];
  }

  //3. traceback to produce one optimal alignment.

  protected void traceback()
  {
    int i=A.length()-1;
    int k=B.length()-1;

    while(i>0 || k>0)
    {
      if(i>0 && k>0 && TB[i][k]==1)
      {
        nA = A.charAt(i) + nA;
        lines = "|" + lines;
        nB = B.charAt(k) + nB;
        i = i-1;
        k = k-1;
      }  //diagonal

      else if(i>0 && k>0 && TB[i][k]==2)
      {
        nA = A.charAt(i) + nA;
        lines = " " + lines;
        nB = "_" + nB;
        i = i-1; k = k;
      } //above

      else if(i>0 && k>0 && TB[i][k]==3)
      {
        nA = "_" + nA;
        lines = " " + lines;
        nB = B.charAt(k) + nB;
        k = k-1; i = i;
      } //left

      if(i==0 || k==0)
        break;
    }
  }//traceback

  //4. run the first three stages in turn, print out final alignment

    protected void run()
  {
    initialize();
    fill();
    traceback();

    System.out.println();
    System.out.println("Generated random sequences: ");
    System.out.println(A.substring(1));
    System.out.println(B.substring(1));
    System.out.println();

    System.out.println("Optimal alignment: ");
    System.out.println(nA);
    System.out.println(lines);
    System.out.println(nB);
    System.out.println("Alignment score is "+alignscore());
  }

  public static void main(String[] av)
  {
    //input your own string
    String A = av[0];
    String B = av[1];

    dna align = new dna(A,B);  // take command line input
    align.run();
  }

}
