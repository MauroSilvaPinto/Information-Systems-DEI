package Torre_Hanoi;

import java.util.Scanner;

public class TorreHanoi_mauro {
	public static void main(String args[]) {

		int n_rods = 0;
		boolean n_rods_input = false;
		int optimal_n_movs = 0;

		boolean final_form = false;
		boolean abort = false;

		int n_movs = 0;
		int player_movement = 0;
		boolean player_mov_input = false;

		Scanner scan = new Scanner(System.in);

		System.out.println("********************* Tower of Hanoi *********************\n");

		// This validates the number of rods
		// first sees if the number is an integer
		// then checks if it is above or equal to 3
		while (n_rods_input == false) {
			System.out.println("Number of rods:");
			if (!scan.hasNextInt()) {
				String a=scan.nextLine();
				System.out.println(a);
				System.out.println("Input is not an integer.");
				System.out.println("");
				
			} else {
				n_rods = scan.nextInt();
				if (n_rods < 3) {
					System.out.println("Input is under 3.");
					System.out.println("");
					scan.nextLine();
				} else {
					n_rods_input = true;
					scan.nextLine();
				}
			}
		}

		// building the sticks
		int[] stick_1 = new int[n_rods];
		int[] stick_2 = new int[n_rods];
		int[] stick_3 = new int[n_rods];

		// constructing the sticks
		for (int i = 0; i < n_rods; i++) {
			stick_1[i] = n_rods - i;
			stick_2[i] = 0;
			stick_3[i] = 0;
		}

		// determining the optimal number of movements
		optimal_n_movs = (int) (Math.pow(2, n_rods) - 1);

		System.out.print("->The optimal number of movements for ");
		System.out.print(n_rods + " rods is  " + optimal_n_movs);
		System.out.println("<-");
		System.out.println("********************************************************");
		System.out.println("");

		// prints of my dearest playground

		// make the initial configuration

		// knowing the base disc length: each disc is longer
		// than the consecutive by 2* and the first one has
		// always 3*
		int base_disc_length = 3 + 2 * (n_rods - 1);

		// the ground is constituted by the 3 base_disc_lengths +
		// 2 spaces to separate each stick +
		// 2 margins
		int ground_size = base_disc_length * 3 + 4;

		// print the scenario

		// obtaining the column sticks
		int stick_1_center = 1 + (base_disc_length - 1) / 2;
		int stick_2_center = stick_1_center + base_disc_length + 1;
		int stick_3_center = stick_2_center + base_disc_length + 1;

		// creating the scenario image
		char[][] environment = new char[n_rods + 3][ground_size];

		while (!final_form && !abort) {
			System.out.println("Number of movements=" + n_movs);
			System.out.println("");

			System.out.print("Tower 1: ");
			for (int i = 0; i < n_rods; i++) {
				System.out.print(stick_1[i] + " ");
			}
			System.out.println("");

			System.out.print("Tower 2: ");
			for (int i = 0; i < n_rods; i++) {
				System.out.print(stick_2[i] + " ");
			}
			System.out.println("");

			System.out.print("Tower 3: ");
			for (int i = 0; i < n_rods; i++) {
				System.out.print(stick_3[i] + " ");
			}
			System.out.println("");
			System.out.println("");

			// creating the white chars, ground and sticks
			for (int i = 0; i < n_rods + 3; i++) {
				for (int j = 0; j < ground_size; j++) {
					// the ground
					if (i == n_rods + 1) {
						environment[i][j] = '#';
						// letters
					} else if (i == n_rods + 2) {
						// the letter A
						if (j == stick_1_center) {
							environment[i][j] = 'A';
							// the letter B
						} else if (j == stick_2_center) {
							environment[i][j] = 'B';
							// the letter C
						} else if (j == stick_3_center) {
							environment[i][j] = 'C';
							// white space
						} else {
							environment[i][j] = ' ';
						}
					} else {
						// the stick
						if (j == stick_1_center || j == stick_2_center || j == stick_3_center) {
							environment[i][j] = '|';
							// white space
						} else {
							environment[i][j] = ' ';
						}
					}
				}
			}

			// filling the rods in the sticks
			// the way i do it: fill the center
			// then i see its value in order to print * into both sides
			for (int i = 0; i < n_rods; i++) {

				// filling the rods in stick_1
				if (stick_1[i] == 0) {
					environment[n_rods - i][stick_1_center] = '|';
				} else {
					environment[n_rods - i][stick_1_center] = '*';
					for (int j = 0; j < stick_1[i]; j++) {
						environment[n_rods - i][stick_1_center + j + 1] = '*';
						environment[n_rods - i][stick_1_center - j - 1] = '*';
					}
				}

				// filling the rods in stick_2
				if (stick_2[i] == 0) {
					environment[n_rods - i][stick_2_center] = '|';
				} else {
					environment[n_rods - i][stick_2_center] = '*';
					for (int j = 0; j < stick_2[i]; j++) {
						environment[n_rods - i][stick_2_center + j + 1] = '*';
						environment[n_rods - i][stick_2_center - j - 1] = '*';
					}
				}

				// filling the rods in stick_3
				if (stick_3[i] == 0) {
					environment[n_rods - i][stick_3_center] = '|';
				} else {
					environment[n_rods - i][stick_3_center] = '*';
					for (int j = 0; j < stick_3[i]; j++) {
						environment[n_rods - i][stick_3_center + j + 1] = '*';
						environment[n_rods - i][stick_3_center - j - 1] = '*';
					}
				}
			}

			// print the environment
			for (int i = 0; i < n_rods + 3; i++) {
				for (int j = 0; j < ground_size; j++) {
					System.out.print(environment[i][j]);
				}
				System.out.println("");
			}

			// verify final form
			if (stick_3[n_rods - 1] == 1) {
				final_form = true;
			}

			if (final_form) {
				System.out.println("Congratulations!!! Done in " + n_movs + " steps");
				if (n_movs == optimal_n_movs) {
					System.out.println("You did it in the minimal number of steps that was " + optimal_n_movs);
				}
			} else {
				System.out.println("Abort? (Y or [N])");
				if (scan.nextLine().equals("N")) {
					abort = true;
					System.out.println("The game has been aborted.");
				} else {
					// movement control (don't forget to activate it again)
					while (player_mov_input == false) {
						System.out.println("Choose Possible Movement:");
						System.out.println("1:A->B     2:A->C     3:B->A     4:B->C     5:C->A     6:C->B");
						if (!scan.hasNextInt()) {
							System.out.println("Input is not an integer.");
							System.out.println("");
							scan.nextLine();
						} else {
							player_movement = scan.nextInt();
							// an invalid move
							if (!(player_movement >= 1 && player_movement <= 6)) {
								System.out.println("You must choose an option between 1 and 6.");
								System.out.println("");
								scan.nextLine();
							} else {
								// a valid move
								player_mov_input = true;
								scan.nextLine();
							}
						}
					}
					// activate it again
					player_mov_input = false;

					// to make a play:
					// i will get the highest rods from the sticks and correspondent index
					// - if the giver stick bar is empty, it is an invalid play
					// - if the giver stick bar is higher than the receiver one, it is an invalid
					// play
					// - otherwise, it is valid
					int index_giver = 0;
					int value_giver = 0;

					// plays 1-2
					if (player_movement <= 2) {
						// search the bar and collect the rods, when i no longer find one, it is over
						for (int i = 0; i < stick_1.length && stick_1[i] != 0; i++) {
							index_giver = i;
							value_giver = stick_1[i];
						}
					} // plays 3-4
					else if (player_movement > 2 && player_movement < 5) {
						for (int i = 0; i < stick_2.length && stick_2[i] != 0; i++) {
							index_giver = i;
							value_giver = stick_2[i];
						}
					} // plays 5-6
					else {
						for (int i = 0; i < stick_3.length && stick_3[i] != 0; i++) {
							index_giver = i;
							value_giver = stick_3[i];
						}
					}
					// to verify if giver is empty or not
					boolean is_giver_empty = (value_giver == 0);

					int index_receiver = 0;
					int value_receiver = 0;

					// plays 3 and 5
					if (player_movement == 3 || player_movement == 5) {
						// search the bar and collect the rods, when i no longer find one, it is over
						for (int i = 0; i < stick_1.length && stick_1[i] != 0; i++) {
							index_receiver = i;
							value_receiver = stick_1[i];
						}
					} // plays 1 and 6
					else if (player_movement == 1 || player_movement == 6) {
						for (int i = 0; i < stick_2.length && stick_2[i] != 0; i++) {
							index_receiver = i;
							value_receiver = stick_2[i];
						}
					} // plays 2 and 4
					else {
						for (int i = 0; i < stick_3.length && stick_3[i] != 0; i++) {
							index_receiver = i;
							value_receiver = stick_3[i];
						}
					}
					// to verify if giver is lower than receiver or if it is empty, of course
					boolean is_giver_lower_than_receiver = value_giver < value_receiver || value_receiver == 0;
					boolean is_receiver_full = index_receiver == n_rods;

					boolean valid_move = is_giver_lower_than_receiver && !is_giver_empty && !is_receiver_full;

					if (!valid_move) {
						System.out.println("Not a valid move bro");
					} else { // make the move
						if (player_movement <= 2) {
							if (player_movement == 1) {
								stick_1[index_giver] = 0;
								if (value_receiver == 0) { // if it is the first element, you can simply place it
									stick_2[index_receiver] = value_giver;
								} else {
									stick_2[index_receiver + 1] = value_giver; // otherwise, you need to put it above
								}

							} else {
								stick_1[index_giver] = 0;
								if (value_receiver == 0) { // if it is the first element, you can simply place it
									stick_3[index_receiver] = value_giver;
								} else {
									stick_3[index_receiver + 1] = value_giver; // otherwise, you need to put it above
								}
							}
						} else if (player_movement == 3 || player_movement == 4) {
							if (player_movement == 3) {
								stick_2[index_giver] = 0;
								if (value_receiver == 0) { // if it is the first element, you can simply place it
									stick_1[index_receiver] = value_giver;
								} else {
									stick_1[index_receiver + 1] = value_giver; // otherwise, you need to put it above
								}
							} else {
								stick_2[index_giver] = 0;
								if (value_receiver == 0) { // if it is the first element, you can simply place it
									stick_3[index_receiver] = value_giver;
								} else {
									stick_3[index_receiver + 1] = value_giver; // otherwise, you need to put it above
								}
							}
						} else {
							if (player_movement == 5) {
								stick_3[index_giver] = 0;
								if (value_receiver == 0) { // if it is the first element, you can simply place it
									stick_1[index_receiver] = value_giver;
								} else {
									stick_1[index_receiver + 1] = value_giver; // otherwise, you need to put it above
								}
							} else {
								stick_3[index_giver] = 0;
								if (value_receiver == 0) { // if it is the first element, you can simply place it
									stick_2[index_receiver] = value_giver;
								} else {
									stick_2[index_receiver + 1] = value_giver; // otherwise, you need to put it above
								}
							}
						}
						n_movs++;
					}
				}
			}

		}
		scan.close();
	}
}
