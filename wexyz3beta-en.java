// Wexyz is developed by ftvTails (check his github profile)
//
// Russian comments is autor's notes, please ignore that
//
// Before modding, please give credits to me and this version, thanks.

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static String get(String cmd, ArrayList<String> unallowed_cmds, String var_value) {
		String toReturn = var_value;
		for (int i = 0; i < unallowed_cmds.size(); i++) {
			if (unallowed_cmds.get(i) == cmd) {
				//err("Проблема с приватностю", "\"" + cmd + "\" запрещен доступ к переменной");
				toReturn = "";
				break;
			}
		}
		return toReturn;
	}

	private static void wait(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			System.out.println("Runtime error:\n" + e.getStackTrace());
		}
	}

	private static void err(String type, String text) {
		System.out.println(type + ": " + text);
	}

	public static void main(String[] args) throws Exception {
		try {
			System.out.print("init\r");
			int x = 0;
			Scanner cmd_input = new Scanner(System.in); // сканер команд и аргументов (одновременно)
			String cmd_ = "", cmd_args = "", cmd = ""; // сами переменные куда будем сохранять набор команды и аргументов
			ArrayList<String> unallowed_cmds = new ArrayList<String>();
			/*ArrayList<String> ifExpressions = new ArrayList<String>();
			Scanner ifeInput = new Scanner(System.in);
			Scanner ife_cmd_ = new Scanner(System.in);
			Scanner ife_cmd_args = new Scanner(System.in);
			Scanner ifeParser = new Scanner(System.in);*/
			String user_var_type = null, user_var_name = "", user_var_value = ""; // user_var_type будет использован позже
			String bool_value, word_Print; // misc
			Scanner cmd_parser; // нужно для разбора команд и аргументов (и почему я сразу до этого не додумался...)
			System.out.println("    \t       Welcome to Wexyz");
			System.out.println(" - - - - - - - - - Wexyz 3 - - - - - - - - - ");
			while (true) {
				System.out.print("> ");
				cmd_ = cmd_input.next(); // читаем команду
				cmd_args = cmd_input.nextLine(); // читаем аргументы
				cmd = cmd_ + cmd_args; // конкатенация команды и аргументов
				cmd_parser = new Scanner(cmd); // мы же это вроде записывали, верно?
				cmd_parser.next(); // пропуск команды и сразу переход к аргументам
				switch (cmd_) {
				case "print": // вывод строк
					try {

						while (cmd_parser.hasNextLine()) {
							word_Print = cmd_parser.next();
							if (word_Print == "#{" + user_var_name + "}") {
								System.out.print(get(cmd, unallowed_cmds, user_var_value) + " ");
							} else {
								System.out.print(word_Print + " ");
							}
						}
						System.out.print("\n");
					} catch (NoSuchElementException print_eol) {
						err("Syntac error", "unexpected EOL");
					}
					break;
				case "exit": // выход
					cmd_input.close(); // закрываем сканер команд и аргументов...
					cmd_parser.close(); // ... и разборщика
					while (x != 2) {
						System.out.print(" /\r ");
						wait(200);
						System.out.print(" —\r ");
						wait(200);
						System.out.print(" \\\r ");
						wait(200);
						System.out.print(" |\r ");
						wait(200);
						x++;
					}
					Runtime r = Runtime.getRuntime();
					Process p = r.exec("clear");
					BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = "";
					while ((line = b.readLine()) != null) {
						System.out.println(line);
					}

					b.close();
					System.exit(0); // завершаем работу программы с кодом 0
					break;
				case "int":
					user_var_type = "integer";
					user_var_name = cmd_parser.next();
					user_var_value = cmd_parser.nextLine();
					break;
				case "string":
					user_var_type = "string";
					user_var_name = cmd_parser.next();
					user_var_value = cmd_parser.nextLine();
					break;
				case "bool":
					user_var_type = "bool";
					user_var_name = cmd_parser.next();
					bool_value = cmd_parser.next();
					switch (bool_value) {
					case "true":
						user_var_value = "true";
						break;
					case "false":
						user_var_value = "false";
						break;
					default:
						err("Type error", "\"" + bool_value + "\" cannot be used for this variable type");
						break;
					}
					bool_value = null;
					break;
				case "set":
					if (cmd_parser.next() == user_var_name) {
						if (cmd_parser.next() == "=") {
							user_var_value = cmd_parser.nextLine();
						} else {
							err("Syntax error", "Invalid equality operator");
						}
					} else {
						err("Wexyz", "variable not found");
					}
					break;
				case "private":
					unallowed_cmds.add(cmd_parser.next());
					System.out.println("Now the given command is denied access to the variable");
					break;
				case "#":
					cmd_parser.nextLine();
					break;
				case "if":
					if (cmd_parser.next() == user_var_name) {
						if (cmd_parser.next() == "==") {
							if (user_var_value == cmd_parser.next()) {
								switch (cmd_parser.next()) {
								case "print": // вывод строк
									try {

										while (cmd_parser.hasNextLine()) {
											word_Print = cmd_parser.next();
											if (word_Print == "#{" + user_var_name + "}") {
												System.out.print(get(cmd, unallowed_cmds, user_var_value) + " ");
											} else {
												System.out.print(word_Print + " ");
											}
										}
										System.out.print("\n");
									} catch (NoSuchElementException print_eol) {
										err("Syntax error", "unexpected EOL");
									}
									break;
								case "exit": // выход
									cmd_input.close(); // закрываем сканер команд и аргументов...
									cmd_parser.close(); // ... и разборщика
									while (x != 2) {
										System.out.print("/\r ");
										wait(200);
										System.out.print("—\r ");
										wait(200);
										System.out.print("\\\r ");
										wait(200);
										System.out.print("|\r ");
										wait(200);
										x++;
									}
									Runtime t = Runtime.getRuntime();
									Process a = t.exec("clear");
									BufferedReader c = new BufferedReader(new InputStreamReader(a.getInputStream()));
									//String line2 = "";
									while ((line = c.readLine()) != null) {
										System.out.println(line);
									}

									c.close();
									System.exit(0); // завершаем работу программы с кодом 0
									break;
								case "int":
									user_var_type = "integer";
									user_var_name = cmd_parser.next();
									user_var_value = cmd_parser.nextLine();
									break;
								case "string":
									user_var_type = "string";
									user_var_name = cmd_parser.next();
									user_var_value = cmd_parser.nextLine();
									break;
								case "bool":
									user_var_type = "bool";
									user_var_name = cmd_parser.next();
									bool_value = cmd_parser.next();
									switch (bool_value) {
									case "true":
										user_var_value = "true";
										break;
									case "false":
										user_var_value = "false";
										break;
									default:
										err("Type error", "\"" + bool_value + "\" cannot be used for this variable type");
										break;
									}
									bool_value = null;
									break;
								case "set":
									if (cmd_parser.next() == user_var_name) {
										if (cmd_parser.next() == "=") {
											user_var_value = cmd_parser.nextLine();
										} else {
											err("Syntax error", "equality operator expected");
										}
									} else {
										err("Wexyz", "variable not found");
									}
									break;
								case "private":
									unallowed_cmds.add(cmd_parser.next());
									System.out.println("Now the given command is denied access to the variable");
									break;
								case "#":
									cmd_parser.nextLine();
									break;
								case "if":
									if (cmd_parser.next() == user_var_name) {
										if (cmd_parser.next() == "==") {
											if (user_var_value == cmd_parser.next()) {

											}
										}

									} else {

									}
									break;
								// продается место для команды
								default:
									err("Wexyz", "command not found");
									break;
								}
							}
						} else {
							err("If", "");
						}
					} else {
						err("If", "Invalid equality operator in COMPARISON block");
					}
					break;
				case "#/":
					while(cmd_parser.next()!="//#"){
						cmd_input.nextLine();
					}
					break;
				// продается место для команды
				default:
					err("Wexyz", "command not found");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Runtime error\n" + e.getStackTrace());
		} finally {

		}
	}

}