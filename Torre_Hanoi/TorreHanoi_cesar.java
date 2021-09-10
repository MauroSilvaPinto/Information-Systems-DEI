package Torre_Hanoi;

import java.util.Scanner;

public class TorreHanoi_cesar {

	public static void main(String args[])
	{
		int nd=3;
		Scanner sc=new Scanner(System.in);
		System.out.println("***************Tower of Hanoi***************");
		System.out.println("Number of Rods:");
		nd=sc.nextInt();
		int opt_steps=(int)Math.pow(2,nd)-1;
		System.out.println("->The optimal number of movements for "+nd+" rods is "+opt_steps+"<-");

		int poste1[]=new int[nd];
		int poste2[]=new int[nd];
		int poste3[]=new int[nd];
		int disc_dim[]=new int[nd];
		int move=1;
		boolean exit;
		boolean success=false;
		boolean abort=false;
		int nmove=0;


		int nd_p1=nd;
		int nd_p2=0;
		int nd_p3=0;
		int nHorz;
		int nVert;



		/*Inicializar poste1 e calcular tamanho dos discos*/

		for(int i=nd-1;i>=0;i--)
		{
			poste1[i]=i;
			disc_dim[i]=2*(nd-1-i)+3;

		}

		/*Compute geometry numbers for drawing*/
		nHorz=(6*(nd-1)+12);
		nVert=nd;

		do
		{
			System.out.println("Number of movements="+nmove);//Display number of movements
			System.out.println();
			
			//Draw game
			for(int lev=nVert;lev>=0;lev--)
			{
				int k=0;
				int pos_ocup[]=new int[nHorz-4]; // 4 positions will never have disk
				
				//Evaluate  positions occupied by the rods in pin A
				if(lev<nd_p1)
				{
					int act_disc=poste1[lev];
					int ki=k;
					for(int pp=ki;pp<(ki+disc_dim[act_disc]);pp++)
					{
						pos_ocup[pp]=((nHorz/6)-((disc_dim[act_disc]-1)/2))+(pp-ki);
						k++;

					}


				}
				//Evaluate  positions occupied by the rods in pin B
				if(lev<nd_p2)
				{
					int act_disc=poste2[lev];
					int ki=k;
					for(int pp=ki;pp<(ki+disc_dim[act_disc]);pp++)
					{
						pos_ocup[pp]=((3*nHorz/6)-((disc_dim[act_disc]-1)/2))+(pp-ki);
						k++;

					}


				}
				//Evaluate  positions occupied by the rods in pin C
				if(lev<nd_p3)
				{
					int act_disc=poste3[lev];
					int ki=k;
					for(int pp=ki;pp<(ki+disc_dim[act_disc]);pp++)
					{
						pos_ocup[pp]=((5*nHorz/6)-((disc_dim[act_disc]-1)/2))+(pp-ki);
						k++;

					}


				}
				
				for(int hp=0;hp<nHorz;hp++)
				{
					char ch=' ';

					if(hp==nHorz/6 ||hp==3*nHorz/6 || hp==5*nHorz/6)//Draw pins
					{
						ch='|';
					}
					
					
					
					for(int op=0;op<k;op++)//Draw rods
					{
						if(pos_ocup[op]==hp)
						{
							ch='*';
						}
					}

					System.out.print(ch);

				}
				System.out.println();
			}
			
			//Draw base
			for(int hp=0;hp<=nHorz;hp++)
			{
				System.out.print("#");
			}
			System.out.println();
			String tnames="ABC";
			int i=0;
			for(int hp=0;hp<nHorz;hp++)
			{

				if(hp==nHorz/6 ||hp==3*nHorz/6 || hp==5*nHorz/6)
				{
					System.out.print(tnames.charAt(i));
					i++;
				}
				else
					System.out.print(" ");
			}
			System.out.println();
			
			//Ask to abort the game
			System.out.println("Abort?(Y or [N])");
		
			abort=sc.next().equals("Y");

			if(!abort)
			{

				/*Ask for playing*/
				do 
				{
					System.out.println("Choose Possible Movement:");
					System.out.print("1:A->B     ");
					System.out.print("2:A->C     ");
					System.out.print("3:B->A     ");
					System.out.print("4:B->C     ");
					System.out.print("5:C->A     ");
					System.out.println("6:C->B");

					if(sc.hasNextInt())
					{
						move=sc.nextInt();
						exit=true;
					}
					else
					{
						System.out.println("Command should be an integer between 1 and 6!!");
						exit=false;
						sc.next();
					}
				}while(!exit);

				if(move==1) //A->B movement 
				{
					if(nd_p1>0) //Only possible if at least one rod is in pin A
					{
						if(nd_p2==0) //If pin B is empty then just move rod
						{
							poste2[0]=poste1[nd_p1-1];
							nd_p1--;
							nd_p2++;
						}
						else //If pin B is not empty we must compare rods size and then move rod
						{
							if(poste1[nd_p1-1]>poste2[nd_p2-1])
							{
								poste2[nd_p2]=poste1[nd_p1-1];
								nd_p1--;
								nd_p2++;
							}
							else
							{
								System.out.println("Movement impossible Disk1 bigger than Disk2");
							}
						}
					}
					else
					{
						System.out.println("A is empty!!!");
					}
				}
				else if(move==2) //A->C movement 
				{
					if(nd_p1>0)
					{
						if(nd_p3==0)
						{
							poste3[0]=poste1[nd_p1-1];
							nd_p1--;
							nd_p3++;
						}
						else
						{
							if(poste1[nd_p1-1]>poste3[nd_p3-1])
							{
								poste3[nd_p3]=poste1[nd_p1-1];
								nd_p1--;
								nd_p3++;
							}
							else
							{
								System.out.println("Movement impossible Disk1 bigger than Disk3");
							}
						}
					}
					else
					{
						System.out.println("A is empty!!!");
					}
				}
				else if(move==3)//B->A movement
				{
					if(nd_p2>0)
					{
						if(nd_p1==0)
						{
							poste1[0]=poste2[nd_p2-1];
							nd_p1++;
							nd_p2--;
						}
						else
						{
							if(poste1[nd_p1-1]<poste2[nd_p2-1])
							{
								poste1[nd_p1]=poste2[nd_p2-1];
								nd_p1++;
								nd_p3--;
							}
							else
							{
								System.out.println("Movement impossible Disk2 bigger than Disk1");
							}
						}
					}
					else
					{
						System.out.println("B is empty!!!");
					}
				}

				else if(move==4)//B->C movement
				{
					if(nd_p2>0)
					{
						if(nd_p3==0)
						{
							poste3[0]=poste2[nd_p2-1];
							nd_p3++;
							nd_p2--;
						}
						else
						{
							if(poste2[nd_p2-1]>poste3[nd_p3-1])
							{
								poste3[nd_p3]=poste2[nd_p2-1];
								nd_p3++;
								nd_p2--;
							}
							else
							{
								System.out.println("Movement impossible Disk2 bigger than Disk3");
							}
						}
					}
					else
					{
						System.out.println("B is empty!!!");
					}
				}
				else if(move==5)//C->A movement
				{
					if(nd_p3>0)
					{
						if(nd_p1==0)
						{
							poste1[0]=poste3[nd_p3-1];
							nd_p1++;
							nd_p3--;
						}
						else
						{
							if(poste3[nd_p3-1]>poste1[nd_p1-1])
							{
								poste1[nd_p1]=poste3[nd_p3-1];
								nd_p1++;
								nd_p3--;
							}
							else
							{
								System.out.println("Movement impossible Disk3 bigger than Disk1");
							}
						}
					}
					else
					{
						System.out.println("B is empty!!!");
					}
				}
				else if(move==6)//C->B movement
				{
					if(nd_p3>0)
					{
						if(nd_p2==0)
						{
							poste2[0]=poste3[nd_p3-1];
							nd_p2++;
							nd_p3--;
						}
						else
						{
							if(poste3[nd_p3-1]>poste2[nd_p2-1])
							{
								poste2[nd_p2]=poste3[nd_p3-1];
								nd_p2++;
								nd_p3--;
							}
							else
							{
								System.out.println("Movement impossible Disk3 bigger than Disk2");
							}
						}
					}
					else
					{
						System.out.println("B is empty!!!");
					}
				}

				nmove++;

				if(nd_p3==nd)//Success!!!!
				{
					System.out.println("Congratulations!!!Done in "+nmove+"steps.");

					if(nmove==(Math.pow(2, nd)-1))
					{
						System.out.println("You did it in the minimal number of steps that was "+opt_steps+".");
					}
					else
					{
						System.out.println("You did it in more than the minimal number of steps that was "+opt_steps+".");
					}

					success=true;
				}


			}
			else
			{
				System.out.println("You decided to abort!!! Bye Bye!!!!");
			}


		}
		while(!success && !abort);

		sc.close();

	}

}
