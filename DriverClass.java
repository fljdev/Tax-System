package tax;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("unused")
public class DriverClass extends JFrame {

	private ArrayList<Employee> employees;
	private int i = 0;// used for iterating arrayList

	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;

	// create employee dialog
	private JDialog create;
	private JPanel employeePanel, bottomPanel;
	private JLabel fornameLb, surnameLb, ppsLb, dobLb, monthlyPayLb;
	private JTextField fornameTf, surnameTf, ppsTf;
	private JFormattedTextField monthlyPayTf;
	private JButton addButton, exitButton, taxStatusButton;
	private JFormattedTextField dobTf;

	// update tax details dialog
	private JDialog update;
	private JPanel taxPanel;
	private JPanel updateTaxTitle, updateKids, updateStatus, submit;
	private JButton submitStatus;
	private JLabel title, numberKids, marital;
	private JComboBox status;
	private JSpinner spin;

	// display Employee and tax details Dialogp
	private JDialog displayEmployee;
	private JPanel displayEmpPanel;
	private JLabel dialogTitle, labelForname, labelSurname, labelPPS, labelDOB;
	private JTextField textFieldForname, textFieldSurname, textFieldPPS,
			textFieldDOB;
	// tax
	private JLabel labelSalary, labelAt20, labelAt40, labelLiable;
	private JLabel labelCredits, labelNetTax, labelPrsi, labelUsc;
	private JLabel labelTotDeductions, labelAnnualNet, labelMonthlyNet,
			labelWeeklyNet;
	private JTextField tfSalary, tfAt20, tfAt40, tfLiable;
	private JTextField tfCredits, tfNetTax, tfPrsi, tfUsc;
	private JTextField tfTotDeductions, tfAnnualNet, tfMonthlyNet, tfWeeklyNet;
	// display panel buttons
	private JButton previous, edit, next;

	// Display Summary attributes
	private JDialog summary;
	private JPanel displaySummary;
	private JLabel summaryTitleLb, numberEmployeesLb, totalSalariesLb;
	private JLabel avgSalaryLb, totalEmployeePrsiLb, totalEmployerPrsiLb;
	private JTextField summaryTitleTf, numberEmployeesTf, totalSalariesTf;
	private JTextField avgSalaryTf, totalEmployeePrsiTf, totalEmployerPrsiTf;
	private JButton print, close;

	// summary calculations
	private int numberEmployees;
	private double totalSalaries;
	private double avgSalary;
	private double totalEmployeePrsi;
	private double totalEmployerPrsi;

	// attfibutes for tax calculation
	private int numberChildren;
	private String maritalStatus;

	public DriverClass(String title) {
		super(title);
		employees = new ArrayList<Employee>();
		hardwireEmployees();
		startGui();
	}// end constructor

	Employee emp;
	TaxDetails td;

	public void hardwireEmployees() {
		td = new TaxDetails(5000, 0, "Single/Widowed");
		emp = new Employee("Hank", "Marvin", "555", "07041978", 0, td);
		calculate();
		employees.add(emp);
		numberEmployees++;

		td = new TaxDetails(3000, 5, "Single/Widowed");
		emp = new Employee("Jimi", "Hendrix", "444", "8581", 0, td);
		calculate();
		employees.add(emp);
		numberEmployees++;

		td = new TaxDetails(7500, 0, "Married-1 inc");
		emp = new Employee("Janis", "Joplin", "666", "96555f", 0, td);
		calculate();
		employees.add(emp);
		numberEmployees++;

	}

	/*
	 * methods to get figurs for Summary
	 */
	public void calculateSummary() {
		totalSalaries = 0;
		avgSalary = 0;
		totalEmployeePrsi = 0;
		totalEmployerPrsi = 0;
		for (Employee emp : employees) {
			totalSalaries += (emp.getTaxDetails().getYearlyGross());
			totalEmployeePrsi += (emp.getTaxDetails().getPrsi() * 52);
			totalEmployerPrsi += (emp.getTaxDetails().getPrsiEmployer());

		}
		avgSalary = totalSalaries / numberEmployees;
	}

	/*
	 * Method that calls calculation methods on users input
	 */
	public void calculate() {
		td.getYearlyGross();
		td.getWeeklyGross();
		td.getPrsi();
		td.getUsc();
		td.getBracket();
		td.getTaxCredits();
		td.getAt20();
		td.getAt40();
		td.getTaxLiability();
		td.getTaxDue();
		td.getPrsiEmployer();
	}

	public JMenuBar setUpTheMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu createMenu;
		JMenuItem employeeTaxItem;
		createMenu = new JMenu("Create");
		menuBar.add(createMenu);
		employeeTaxItem = new JMenuItem("Employee/Tax");
		createMenu.add(employeeTaxItem);

		JMenu displayMenu;
		displayMenu = new JMenu("Display");
		menuBar.add(displayMenu);
		JMenuItem employeeItem, summaryItem;
		employeeItem = new JMenuItem("Employee");
		summaryItem = new JMenuItem("Summary");
		displayMenu.add(employeeItem);
		displayMenu.add(summaryItem);

		JMenu ammendMenu;
		ammendMenu = new JMenu("Ammend");
		menuBar.add(ammendMenu);
		JMenuItem deleteItem, editItem;
		deleteItem = new JMenuItem("Delete");
		editItem = new JMenuItem("Edit");
		ammendMenu.add(deleteItem);
		ammendMenu.add(editItem);

		JMenu helpMenu;
		helpMenu = new JMenu("Help?");
		JMenuItem helpItem;
		helpItem = new JMenuItem("Information");
		menuBar.add(helpMenu);
		helpMenu.add(helpItem);

		employeeTaxItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create();
			}
		});
		employeeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayEmployee();
			}
		});
		summaryItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displaySummary();
			}
		});

		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				help();
			}
		});

		return menuBar;
	}

	public void create() {
		System.out.println("create pressed");
		create.setVisible(true);
		addButton.setVisible(false);
		employeePanel.setLayout(new GridLayout(12, 1));
		employeePanel.add(ppsLb);
		employeePanel.add(ppsTf);
		employeePanel.add(fornameLb);
		employeePanel.add(fornameTf);
		employeePanel.add(surnameLb);
		employeePanel.add(surnameTf);

		employeePanel.add(dobLb);
		employeePanel.add(dobTf);
		employeePanel.add(monthlyPayLb);
		employeePanel.add(monthlyPayTf);
		employeePanel.add(bottomPanel);
		bottomPanel.add(taxStatusButton);
		bottomPanel.add(addButton);
		employeePanel.add(exitButton);
	}

	public void displayEmployee() {
		System.out.println("display employee pressed");
		// initiate components for displayEmployee dialog

		displayEmpPanel.setLayout(new GridLayout(20, 1));

		// employee details
		dialogTitle = new JLabel("Employee Tax Details ");
		labelForname = new JLabel("First name         ");
		labelSurname = new JLabel("Second name        ");
		labelPPS = new JLabel("PPS number         ");
		labelDOB = new JLabel("Date of birth      ");
		textFieldForname = new JTextField(15);
		textFieldForname.setEditable(false);
		textFieldSurname = new JTextField(15);
		textFieldSurname.setEditable(false);
		textFieldPPS = new JTextField(15);
		textFieldPPS.setEditable(false);
		textFieldDOB = new JTextField(15);
		textFieldDOB.setEditable(false);

		// tax details
		labelSalary = new JLabel("Salary");
		labelAt20 = new JLabel("Tax Payable at 20 %  ");
		labelAt40 = new JLabel("Tax Payable at 40 % ");
		labelLiable = new JLabel("Total tax  Liablity ");
		labelCredits = new JLabel("Tax credits  ");
		labelNetTax = new JLabel("Net tax Due  ");
		labelPrsi = new JLabel("PRSI   ");
		labelUsc = new JLabel("USC   ");
		labelTotDeductions = new JLabel("Total deduction ");
		labelAnnualNet = new JLabel("Annual Net   ");
		labelMonthlyNet = new JLabel("Monthly Net  ");
		labelWeeklyNet = new JLabel("Weekly Net  ");

		tfSalary = new JTextField(15);
		tfSalary.setEditable(false);
		tfAt20 = new JTextField(15);
		tfAt20.setEditable(false);
		tfAt40 = new JTextField(15);
		tfAt40.setEditable(false);
		tfLiable = new JTextField(15);
		tfLiable.setEditable(false);
		tfCredits = new JTextField(15);
		tfCredits.setEditable(false);
		tfNetTax = new JTextField(15);
		tfNetTax.setEditable(false);
		tfPrsi = new JTextField(15);
		tfPrsi.setEditable(false);
		tfUsc = new JTextField(15);
		tfUsc.setEditable(false);
		tfTotDeductions = new JTextField(15);
		tfTotDeductions.setEditable(false);
		tfAnnualNet = new JTextField(15);
		tfAnnualNet.setEditable(false);
		tfMonthlyNet = new JTextField(15);
		tfMonthlyNet.setEditable(false);
		tfWeeklyNet = new JTextField(15);
		tfWeeklyNet.setEditable(false);

		JPanel fp = new JPanel(new GridLayout());
		JPanel sp = new JPanel(new GridLayout());
		JPanel pp = new JPanel(new GridLayout());
		JPanel dp = new JPanel(new GridLayout());
		JPanel dtp = new JPanel(new GridBagLayout());
		JPanel gap1 = new JPanel();
		// JPanel gap2 = new JPanel();
		JPanel tp1 = new JPanel(new GridLayout());
		JPanel tp2 = new JPanel(new GridLayout());
		JPanel tp3 = new JPanel(new GridLayout());
		JPanel tp4 = new JPanel(new GridLayout());
		JPanel tp5 = new JPanel(new GridLayout());
		JPanel tp6 = new JPanel(new GridLayout());
		JPanel tp7 = new JPanel(new GridLayout());
		JPanel tp8 = new JPanel(new GridLayout());
		JPanel tp9 = new JPanel(new GridLayout());
		JPanel tp10 = new JPanel(new GridLayout());
		JPanel tp11 = new JPanel(new GridLayout());
		JPanel tp12 = new JPanel(new GridLayout());
		JPanel tp13 = new JPanel(new GridLayout());

		previous = new JButton("<< Previous");
		edit = new JButton("--Edit--");
		next = new JButton("Next >>");

		displayEmployee.setVisible(true);

		emp = employees.get(0);

		// employee details
		displayEmpPanel.add(dtp);
		dtp.add(dialogTitle);
		displayEmpPanel.add(fp);
		fp.add(labelForname);
		fp.add(textFieldForname);
		displayEmpPanel.add(sp);
		sp.add(labelSurname);
		sp.add(textFieldSurname);
		displayEmpPanel.add(pp);
		pp.add(labelPPS);
		pp.add(textFieldPPS);
		displayEmpPanel.add(dp);
		dp.add(labelDOB);
		dp.add(textFieldDOB);

		// space between employe and tax
		displayEmpPanel.add(gap1);
		// displayEmpPanel.add(gap2);
		displayEmpPanel.add(tp1);
		tp1.add(labelSalary);
		tp1.add(tfSalary);
		displayEmpPanel.add(tp2);
		tp2.add(labelAt20);
		tp2.add(tfAt20);
		displayEmpPanel.add(tp3);
		tp3.add(labelAt40);
		tp3.add(tfAt40);
		displayEmpPanel.add(tp4);
		tp4.add(labelLiable);
		tp4.add(tfLiable);
		displayEmpPanel.add(tp5);
		tp5.add(labelCredits);
		tp5.add(tfCredits);
		displayEmpPanel.add(tp6);
		tp6.add(labelNetTax);
		tp6.add(tfNetTax);
		displayEmpPanel.add(tp7);
		tp7.add(labelPrsi);
		tp7.add(tfPrsi);
		displayEmpPanel.add(tp8);
		tp8.add(labelUsc);
		tp8.add(tfUsc);
		displayEmpPanel.add(tp9);
		tp9.add(labelTotDeductions);
		tp9.add(tfTotDeductions);
		displayEmpPanel.add(tp10);
		tp10.add(labelAnnualNet);
		tp10.add(tfAnnualNet);
		displayEmpPanel.add(tp11);
		tp11.add(labelMonthlyNet);
		tp11.add(tfMonthlyNet);
		displayEmpPanel.add(tp12);
		tp12.add(labelWeeklyNet);
		tp12.add(tfWeeklyNet);

		displayEmpPanel.add(tp13);
		tp13.add(previous);
		tp13.add(edit);
		tp13.add(next);
		displayEmpPanel.add(exitButton = new JButton("Exit"));

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayEmployee.dispose();

			}
		});

		textFieldForname.setText(emp.getFirstname());
		textFieldSurname.setText(emp.getSurname());
		textFieldPPS.setText(emp.getPps());
		textFieldDOB.setText(emp.getDob());

		int intVal;// used to strip away floating points from numbers

		intVal = (int) emp.getTaxDetails().getYearlyGross();
		tfSalary.setText(String.valueOf(intVal));
		intVal = (int) (emp.getTaxDetails().getAt20() * .2);
		tfAt20.setText(String.valueOf(intVal));
		intVal = (int) (emp.getTaxDetails().getAt40() * .4);
		tfAt40.setText(String.valueOf(intVal));
		intVal = (int) emp.getTaxDetails().getTaxLiability();
		tfLiable.setText(String.valueOf(intVal));
		intVal = (int) emp.getTaxDetails().getTaxCredits();
		tfCredits.setText(String.valueOf(intVal));
		intVal = (int) emp.getTaxDetails().getTaxDue();
		tfNetTax.setText(String.valueOf(intVal));
		intVal = (int) emp.getTaxDetails().getPrsi();
		tfPrsi.setText(String.valueOf(intVal * 52));
		intVal = (int) emp.getTaxDetails().getUsc();
		tfUsc.setText(String.valueOf(intVal));
		intVal = (int) emp.getTaxDetails().getTotalDeductions();
		tfTotDeductions.setText(String.valueOf(intVal));
		intVal = (int) emp.getTaxDetails().getAnnualNet();
		tfAnnualNet.setText(String.valueOf(intVal));
		intVal = (int) emp.getTaxDetails().getMonthlyNet();
		tfMonthlyNet.setText(String.valueOf(intVal));
		intVal = (int) emp.getTaxDetails().getWeeklyNet();
		tfWeeklyNet.setText(String.valueOf(intVal));

		/*
		 * Next
		 */
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				i++;
				if (i < employees.size()) {
					emp = employees.get(i);

					textFieldForname.setText(emp.getFirstname());
					textFieldSurname.setText(emp.getSurname());
					textFieldPPS.setText(emp.getPps());
					textFieldDOB.setText(emp.getDob());

					int intVal;// used to strip away floating points from
								// numbers

					intVal = (int) emp.getTaxDetails().getYearlyGross();
					tfSalary.setText(String.valueOf(intVal));
					intVal = (int) (emp.getTaxDetails().getAt20() * .2);
					tfAt20.setText(String.valueOf(intVal));
					intVal = (int) (emp.getTaxDetails().getAt40() * .4);
					tfAt40.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getTaxLiability();
					tfLiable.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getTaxCredits();
					tfCredits.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getTaxDue();
					tfNetTax.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getPrsi();
					tfPrsi.setText(String.valueOf(intVal * 52));
					intVal = (int) emp.getTaxDetails().getUsc();
					tfUsc.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getTotalDeductions();
					tfTotDeductions.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getAnnualNet();
					tfAnnualNet.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getMonthlyNet();
					tfMonthlyNet.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getWeeklyNet();
					tfWeeklyNet.setText(String.valueOf(intVal));

				} else {
					JOptionPane.showMessageDialog(null,
							"End of employee list reached",
							"No More Employees", JOptionPane.WARNING_MESSAGE);
				}// end else
			}
		});// end next action listener

		/*
		 * Previous
		 */
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				i--;
				if (i >= 0) {

					emp = employees.get(i);

					textFieldForname.setText(emp.getFirstname());
					textFieldSurname.setText(emp.getSurname());
					textFieldPPS.setText(emp.getPps());
					textFieldDOB.setText(emp.getDob());

					int intVal;// used to strip away floating points from
								// numbers

					intVal = (int) emp.getTaxDetails().getYearlyGross();
					tfSalary.setText(String.valueOf(intVal));
					intVal = (int) (emp.getTaxDetails().getAt20() * .2);
					tfAt20.setText(String.valueOf(intVal));
					intVal = (int) (emp.getTaxDetails().getAt40() * .4);
					tfAt40.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getTaxLiability();
					tfLiable.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getTaxCredits();
					tfCredits.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getTaxDue();
					tfNetTax.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getPrsi();
					tfPrsi.setText(String.valueOf(intVal * 52));
					intVal = (int) emp.getTaxDetails().getUsc();
					tfUsc.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getTotalDeductions();
					tfTotDeductions.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getAnnualNet();
					tfAnnualNet.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getMonthlyNet();
					tfMonthlyNet.setText(String.valueOf(intVal));
					intVal = (int) emp.getTaxDetails().getWeeklyNet();
					tfWeeklyNet.setText(String.valueOf(intVal));

				} else {

					JOptionPane.showMessageDialog(null,
							"End of employee list reached",
							"No More Employees", JOptionPane.WARNING_MESSAGE);
				}// end else
			}
		});// end previous

		/*
		 * EDIT
		 */

		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Employee employeeToEdit = null;
				boolean ppsFound = false;

				String ppsToEdit = JOptionPane.showInputDialog(null,
						"Enter PPS of employee you wish to edit");
				for (Employee employeeSearch : employees) {

					String ppsInArray;
					ppsInArray = employeeSearch.getPps();

					if (ppsInArray.equalsIgnoreCase(ppsToEdit)) {
						employeeToEdit = employeeSearch;
						ppsFound = true;
					}// end if
				}// end for each

				if (ppsFound) {
					JOptionPane.showMessageDialog(null,
							"PPS found, prepare to update tax details");
					update.setVisible(true);
					taxPanel.setLayout(new GridLayout(4, 1));
					taxPanel.add(updateTaxTitle);
					updateTaxTitle.add(title);
					taxPanel.add(updateKids);
					updateKids.add(numberKids);
					updateKids.add(spin);
					updateStatus.add(marital);
					updateStatus.add(status);
					taxPanel.add(updateStatus);
					taxPanel.add(submit);
					submit.add(submitStatus);

					// employee object details
					String name1 = employeeToEdit.getFirstname();
					String name2 = employeeToEdit.getSurname();
					String dob = employeeToEdit.getDob();
					String ppsNum = employeeToEdit.getPps();
					// tax object details
					double monthly = employeeToEdit.getMonthlySalary();
					numberChildren = (Integer) spin.getValue();
					maritalStatus = (String) status.getSelectedItem();

					td = new TaxDetails(monthly, numberChildren, maritalStatus);
					emp = new Employee(name1, name2, ppsNum, dob, monthly, td);
					calculate();
					employees.remove(employeeToEdit);
					employees.add(emp);

					submitStatus.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ev) {
							JOptionPane.showMessageDialog(null,
									"Employee Details updated");
							displayEmployee.dispose();
						}
					});

				} else {
					JOptionPane.showMessageDialog(null,
							"No Matching PPS found", "Not Found",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}// end display employee

	public void displaySummary() {
		calculateSummary();
		summary.setVisible(true);
		JPanel s1 = new JPanel(new GridLayout());
		s1.setBackground(Color.CYAN);
		JPanel s2 = new JPanel(new GridBagLayout());
		s2.setBackground(Color.CYAN);
		JPanel s3 = new JPanel(new GridLayout());
		s3.setBackground(Color.CYAN);
		JPanel s4 = new JPanel(new GridLayout());
		s4.setBackground(Color.CYAN);
		JPanel s5 = new JPanel(new GridLayout());
		s5.setBackground(Color.CYAN);
		JPanel s6 = new JPanel(new GridLayout());
		s6.setBackground(Color.CYAN);
		JPanel s7 = new JPanel(new GridLayout());
		s7.setBackground(Color.CYAN);
		JPanel s8 = new JPanel(new GridLayout());
		s8.setBackground(Color.CYAN);
		JPanel s9 = new JPanel(new GridLayout());
		s9.setBackground(Color.CYAN);
		JPanel s10 = new JPanel(new GridLayout());
		s10.setBackground(Color.CYAN);
		displaySummary.setLayout(new GridLayout(10, 1));

		displaySummary.setBackground(Color.YELLOW);
		displaySummary.add(s1);
		displaySummary.add(s2);
		s2.add(summaryTitleLb);
		displaySummary.add(s3);
		displaySummary.add(s4);
		s4.add(numberEmployeesLb);
		s4.add(numberEmployeesTf);
		displaySummary.add(s5);
		s5.add(totalSalariesLb);
		s5.add(totalSalariesTf);
		displaySummary.add(s6);
		s6.add(avgSalaryLb);
		s6.add(avgSalaryTf);
		displaySummary.add(s7);
		s7.add(totalEmployeePrsiLb);
		s7.add(totalEmployeePrsiTf);
		displaySummary.add(s8);
		s8.add(totalEmployerPrsiLb);
		s8.add(totalEmployerPrsiTf);
		displaySummary.add(s9);
		displaySummary.add(s10);
		s10.add(print);
		s10.add(close);

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				summary.dispose();
			}
		});

		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Printer not found, please check cable");
				summary.dispose();

			}
		});

		int intVal;
		numberEmployeesTf.setText((String.valueOf(numberEmployees)));
		intVal = (int) totalSalaries;
		totalSalariesTf.setText((String.valueOf(intVal)));
		intVal = (int) totalEmployeePrsi;
		totalEmployeePrsiTf.setText((String.valueOf(intVal)));
		intVal = (int) avgSalary;
		avgSalaryTf.setText((String.valueOf(intVal)));
		intVal = (int) (totalEmployerPrsi * 52);
		totalEmployerPrsiTf.setText((String.valueOf(intVal)));
	}

	public void delete() {
		System.out.println("delete pressed");
		Employee employeeToDelete = null;
		boolean ppsFound = false;

		String ppsToDelete = JOptionPane.showInputDialog(null,
				"Enter PPS of employee to remove");
		for (Employee employeeScan : employees) {

			String ppsInArray;
			ppsInArray = employeeScan.getPps();

			if (ppsInArray.equalsIgnoreCase(ppsToDelete)) {
				employeeToDelete = employeeScan;
				ppsFound = true;
			}// end if
		}// end for each

		if (ppsFound) {
			employees.remove(employeeToDelete);
			numberEmployees--;
			JOptionPane.showMessageDialog(null, employeeToDelete.getFirstname()
					+ " will be deleted", "Desctuction Complete",
					JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No Matching PPS found",
					"Not Found", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void edit() {
		System.out.println("edit pressed");
		Employee employeeToEdit = null;
		boolean ppsFound = false;

		String ppsToEdit = JOptionPane.showInputDialog(null,
				"Enter PPS of employee you wish to edit");
		for (Employee employeeSearch : employees) {

			String ppsInArray;
			ppsInArray = employeeSearch.getPps();

			if (ppsInArray.equalsIgnoreCase(ppsToEdit)) {
				employeeToEdit = employeeSearch;
				ppsFound = true;
			}// end if
		}// end for each

		if (ppsFound) {
			JOptionPane.showMessageDialog(null,
					"PPS found, prepare to update tax details");
			update.setVisible(true);
			taxPanel.setLayout(new GridLayout(4, 1));
			taxPanel.add(updateTaxTitle);
			updateTaxTitle.add(title);
			taxPanel.add(updateKids);
			updateKids.add(numberKids);
			updateKids.add(spin);
			updateStatus.add(marital);
			updateStatus.add(status);
			taxPanel.add(updateStatus);
			taxPanel.add(submit);
			submit.add(submitStatus);

			// employee object details
			String name1 = employeeToEdit.getFirstname();
			String name2 = employeeToEdit.getSurname();
			String dob = employeeToEdit.getDob();
			String ppsNum = employeeToEdit.getPps();
			// tax object details
			double monthly = employeeToEdit.getMonthlySalary();
			numberChildren = (Integer) spin.getValue();
			maritalStatus = (String) status.getSelectedItem();

			td = new TaxDetails(monthly, numberChildren, maritalStatus);
			emp = new Employee(name1, name2, ppsNum, dob, monthly, td);
			calculate();
			employees.remove(employeeToEdit);
			employees.add(emp);

			submitStatus.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					JOptionPane.showMessageDialog(null,
							"Employee Details updated");
					displayEmployee.dispose();
				}
			});

		} else {
			JOptionPane.showMessageDialog(null, "No Matching PPS found",
					"Not Found", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void help() {
		JOptionPane.showMessageDialog(null,
				"A Customer Service Agent will be with you shortly\n"
				+ "Please wait patiently",
				"Customer Service Department", JOptionPane.INFORMATION_MESSAGE);

	}

	public void startGui() {
		Container content = getContentPane();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		content.setBackground(Color.BLACK);
		setJMenuBar(setUpTheMenu());

		Icon icon = new ImageIcon("tx.jpeg");
		JPanel pixPan = new JPanel();
		JLabel pix = new JLabel(icon);
		content.add(pixPan);
		pixPan.add(pix);

		// setting up frame
		setSize(300, 180);
		setLocation(150, 130);
		setResizable(true);
		setVisible(true);
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		// create three dialods for input and display
		create = new JDialog();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		create.setBounds(400, 100, 400, 400);
		employeePanel = new JPanel();
		employeePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		create.add(employeePanel);

		update = new JDialog();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		update.setBounds(600, 100, 270, 200);
		taxPanel = new JPanel();
		taxPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		update.add(taxPanel);

		displayEmployee = new JDialog();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		displayEmployee.setBounds(600, 100, 400, 1500);
		displayEmpPanel = new JPanel();
		displayEmpPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		displayEmployee.add(displayEmpPanel);

		summary = new JDialog();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		summary.setBounds(400, 100, 400, 400);
		displaySummary = new JPanel();
		displaySummary.setBorder(new EmptyBorder(5, 5, 5, 5));
		summary.add(displaySummary);
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		// instantiate components for the create employee dialog
		ppsLb = new JLabel("PPS Number");
		ppsLb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ppsTf = new JTextField();
		ppsTf.setColumns(10);

		fornameLb = new JLabel("First Name");
		fornameLb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fornameTf = new JTextField();
		fornameTf.setColumns(10);

		surnameLb = new JLabel("Surname");
		surnameLb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		surnameTf = new JTextField();
		surnameTf.setColumns(10);

		dobLb = new JLabel("Date Of Birth");
		dobLb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dobTf = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
		dobTf.setColumns(10);

		monthlyPayLb = new JLabel("Monthly Salary");
		monthlyPayLb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		monthlyPayTf = new JFormattedTextField(new DecimalFormat("######"));
		monthlyPayTf.setColumns(10);
		monthlyPayTf.setValue(new Integer(0000));

		bottomPanel = new JPanel(new FlowLayout());
		taxStatusButton = new JButton("Update Tax");
		addButton = new JButton("Add Employee");
		exitButton = new JButton("Exit");
		submitStatus = new JButton("Submit");

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		// instantiate components for the update tax dialog
		updateTaxTitle = new JPanel();
		updateTaxTitle.setLayout(new GridBagLayout());
		title = new JLabel("ENTER CIRCUMSTANCES");

		SpinnerModel sm = new SpinnerNumberModel(0, 0, 9, 1);
		spin = new JSpinner(sm);
		spin.setSize(20, 20);
		updateKids = new JPanel();
		updateKids.setLayout(new GridBagLayout());
		numberKids = new JLabel("Number of Kids: ");
		updateStatus = new JPanel();
		updateStatus.setLayout(new GridBagLayout());
		marital = new JLabel("Marital Status: ");
		String[] circs = { "Single/Widowed", "Child Carer", "Married-1 inc",
				"Married 2 inc." };
		status = new JComboBox(circs);
		submit = new JPanel();
		submit.setLayout(new GridBagLayout());

		taxStatusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create.setEnabled(false);
				update.setVisible(true);
				taxPanel.setLayout(new GridLayout(4, 1));
				taxPanel.add(updateTaxTitle);
				updateTaxTitle.add(title);
				taxPanel.add(updateKids);
				updateKids.add(numberKids);
				updateKids.add(spin);
				updateStatus.add(marital);
				updateStatus.add(status);
				taxPanel.add(updateStatus);
				taxPanel.add(submit);
				submit.add(submitStatus);

			}
		});
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		/*
		 * initiate components for summary dialod
		 */

		summaryTitleLb = new JLabel("Summary of Financial Position");
		numberEmployeesLb = new JLabel("Number of employees");
		totalSalariesLb = new JLabel("Total salaries");
		avgSalaryLb = new JLabel("Average Salary");
		totalEmployeePrsiLb = new JLabel("Total Employee PRSI");
		totalEmployerPrsiLb = new JLabel("Total Employer PRSI");

		numberEmployeesTf = new JTextField(10);
		numberEmployeesTf.setEditable(false);
		totalSalariesTf = new JTextField(10);
		totalSalariesTf.setEditable(false);
		avgSalaryTf = new JTextField(10);
		avgSalaryTf.setEditable(false);
		totalEmployeePrsiTf = new JTextField(10);
		totalEmployeePrsiTf.setEditable(false);
		totalEmployerPrsiTf = new JTextField(10);
		totalEmployerPrsiTf.setEditable(false);
		print = new JButton("Print");
		close = new JButton("Close");

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create.dispose();
			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				employees.add(emp);
				numberEmployees++;
			}
		});

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		submitStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				addButton.setVisible(true);
				create.setEnabled(true);
				update.dispose();

				// hitting "update tax" button will create employee with details
				// just input
				// bringing user to tax dialog
				String name1 = fornameTf.getText();
				String name2 = surnameTf.getText();
				String dob = dobTf.getText();
				String ppsNum = ppsTf.getText();
				double monthly = Double.parseDouble(monthlyPayTf.getText());

				numberChildren = (Integer) spin.getValue();
				maritalStatus = (String) status.getSelectedItem();
				double spouseDoubleValue = 0;

				if (status.getSelectedItem().equals("Married 2 inc.")) {
					String spouseSalary = JOptionPane.showInputDialog(null,
							"Enter your spouses salary");

					spouseDoubleValue = (double) ((Double
							.parseDouble(spouseSalary)));

				}

				td = new TaxDetails(monthly, numberChildren, maritalStatus);
				emp = new Employee(name1, name2, ppsNum, dob, monthly, td);

				if (spouseDoubleValue > (monthly * 12)) {
					td.setBracketDown();
				}
				calculate();
			}
		});// end actionlistener

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

	}// end startGui

	public static void main(String[] args) {
		DriverClass driver = new DriverClass("Revenue Programme");
	}// end main
}// end DriverClass