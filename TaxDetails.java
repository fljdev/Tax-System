package tax;


public class TaxDetails {

	private double monthlyGross;
	private int numberKids;
	private String maritalStatus;

	public TaxDetails(double monthlyGross, int numberKids, String maritalStatus) {
		this.monthlyGross = monthlyGross;
		this.numberKids = numberKids;
		this.maritalStatus = maritalStatus;
	}// end constructor

	public TaxDetails() {
		this.monthlyGross = 0.0;
		this.numberKids = 0;
		this.maritalStatus = "";
	}

	public double getMonthlyGross() {
		return monthlyGross;
	}

	public void setMonthlyGross(double monthlyGross) {
		this.monthlyGross = monthlyGross;
	}

	public int getNumberKids() {
		return numberKids;
	}

	public void setNumberKids(int numberKids) {
		this.numberKids = numberKids;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	double yearlyGross;

	public double getYearlyGross() {
		yearlyGross = getMonthlyGross() * 12;
		return yearlyGross;
	}

	double weeklyGross;

	public double getWeeklyGross() {
		weeklyGross = monthlyGross / 4.33;
		return weeklyGross;
	}

	/*
	 * PRSI
	 */
	double prsi;

	public double getPrsi() {
		prsi = 0.0;
		double at85 = 0;
		double at105 = 0;

		if (weeklyGross <= 352) {
			prsi = 0;
			employerPrsi = weeklyGross * .085;
		} else {
			prsi = weeklyGross * .04;

			at85 = 356;
			at105 = (weeklyGross - 365);

			employerPrsi += (at85 * .085);
			employerPrsi += (at105 * .1075);
		}// end else

		// prsi*=52;
		return prsi;
	}// end prsi method
	
	double employerPrsi;
	public double getPrsiEmployer(){
		
		employerPrsi = 0.0;
		double at85 = 0;
		double at105 = 0;

		if (weeklyGross <= 352) {
			prsi = 0;
			employerPrsi = weeklyGross * .085;
		} else {
			prsi = weeklyGross * .04;

			at85 = 356;
			at105 = (weeklyGross - 365);

			employerPrsi += (at85 * .085);
			employerPrsi += (at105 * .1075);
		}// end else
		
		return employerPrsi;
	}

	/**
	double employerPrsi;
	public double getEmployerPrsi() {
		prsi = 0.0;
		double at85 = 0;
		double at105 = 0;

		if (weeklyGross <= 352) {
		
			employerPrsi = weeklyGross * .085;
		} else {
			

			at85 = 356;
			at105 = (weeklyGross - 365);

			employerPrsi += (at85 * .085);
			employerPrsi += (at105 * .1075);
		}// end else
		return employerPrsi;
	}// end prsi method
	*/

	/*
	 * USC
	 */
	double usc;

	public double getUsc() {
		double liable;
		double cap4 = 70044;
		double cap3 = 17576;
		double cap2 = 12012;
		double rate4 = .08;
		double rate3 = .07;
		double rate2 = .035;
		double rate1 = .015;

		usc = 0.0;

		liable = yearlyGross;
		if (yearlyGross >= cap4) {
			usc += ((liable - cap4) * rate4);
			liable = cap4;
		}
		if (yearlyGross >= cap3) {
			usc += ((liable - cap3) * rate3);
			liable = cap3;
		}
		if (yearlyGross >= cap2) {
			usc += ((liable - cap2) * rate2);
			liable = cap2;
		}
		usc += (liable * rate1);

		return usc;
	}// end USC

	/*
	 * Tax bracket(20-40)
	 */

	double bracket;
	int selection;
	
	public void setBracketDown(){
		bracket = bracket-18000;
	}

	public double getBracket() {
		if (getMaritalStatus().equalsIgnoreCase("Single/Widowed")) {
			bracket += 33800;
			selection = 1;
		} else if (getMaritalStatus().equalsIgnoreCase("Child Carer")) {
			bracket += 37800;
			selection = 3;
		} else if (getMaritalStatus().equalsIgnoreCase("Married-1 inc")) {
			bracket += 42800;
			selection = 4;
		} else if (getMaritalStatus().equalsIgnoreCase("Married 2 inc.")) {
			bracket += 42800;
			selection = 4;
		}

		// add exemption increase for number of children
		double lowEx = 575;
		double highEx = 830;
		double exDiff = (highEx - lowEx);
		int numberOfChildren = getNumberKids();

		if (numberOfChildren > 0) {
			bracket += highEx * numberOfChildren;
		}
		if (numberOfChildren == 1) {
			bracket -= exDiff;
		} else if (numberOfChildren >= 2) {
			bracket = bracket - (exDiff * 2);
		}
		return bracket;
	}// end bracket

	/*
	 * at20 and at40
	 */
	double at20;
	double at40;

	public double getAt20() {
		if (yearlyGross <= bracket) {
			at20 = yearlyGross;
		} else {
			at40 = yearlyGross - bracket;
			at20 = bracket;
		}
		return at20;
	}// end at20

	public double getAt40() {
		if (yearlyGross <= bracket) {
			at20 = yearlyGross;
		} else {
			at40 = yearlyGross - bracket;
			at20 = bracket;
		}
		return at40;
	}

	// end at20 and at40

	/*
	 * Tax Payable
	 */
	double taxLiability;

	public double getTaxLiability() {
		taxLiability = ((at40 * .4) + (at20 * .2));
		return taxLiability;
	}

	/*
	 * Tax credits
	 */

	double taxCredit;

	public double getTaxCredits() {
		taxCredit = 0.0;
		double singleCredit = 1650;// single
		double widowCredit = 2190;// wido or surviving
		double payeCredit = 1650;// everyody
		double marriedCredit = 3300;// if married
		double singleChildCarerCredit = 1650;
		double ageSingleCredit = 245;
		double ageMarriedCredit = 490;

		if (selection == 1) {
			taxLiability -= payeCredit + singleCredit;
			taxCredit += payeCredit + singleCredit;
		} else if (selection == 2) {
			taxLiability -= payeCredit + widowCredit;
			taxCredit += payeCredit + widowCredit;
		} else if (selection == 3) {
			taxLiability -= payeCredit + singleCredit + singleChildCarerCredit;
			taxCredit += payeCredit + singleCredit + singleChildCarerCredit;
		} else if (selection == 4) {
			taxLiability -= payeCredit + marriedCredit;
			taxCredit += payeCredit + marriedCredit;
		}

		return taxCredit;
	}

	// end taxCredit

	/*
	 * Tax due
	 */
	double taxDue;

	public double getTaxDue() {
		taxDue = taxLiability - taxCredit;
		return taxDue;
	}

	// end tax due

	/*
	 * total deductions
	 */
	double totalDeductions;

	public double getTotalDeductions() {
		totalDeductions = taxLiability + usc + (prsi * 52);
		return totalDeductions;
	}

	double annualNet;

	public double getAnnualNet() {
		annualNet = yearlyGross - totalDeductions;
		return annualNet;
	}

	double monthlyNet;

	public double getMonthlyNet() {
		monthlyNet = annualNet / 12;
		return monthlyNet;

	}

	double weeklyNet;

	public double getWeeklyNet() {

		weeklyNet = monthlyNet / 4.33;

		return weeklyNet;
	}

}// end tax details class

