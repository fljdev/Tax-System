package tax;





public class Employee {

	private String pps;
	private String surname;
	private String forename;
	private String dob;
	private double monthlySalary;
	private TaxDetails td = new TaxDetails();
	public Employee() {

		this.pps = "";
		this.surname = "";
		this.forename = "";
		this.dob = "";
		this.monthlySalary = 0.0;
		this.td = null;
	}

	public Employee(String forename,String surname,String pps , String dob,
			double monthlySalary, TaxDetails td) {

		this.pps = pps;
		this.surname = surname;
		this.forename = forename;
		this.dob = dob;
		this.monthlySalary = monthlySalary;
		this.td = td;
	}

	public String getPps() {
		return pps;
	}

	public void setPps(String pps) {
		this.pps = pps;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstname() {
		return forename;
	}

	public void setFirstname(String forename) {
		this.forename = forename;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public double getMonthlySalary() {
		return monthlySalary;
	}

	public void setMonthlySalary(double monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	public TaxDetails getTaxDetails() {
		return td;
	}

	public void setTaxDetails(TaxDetails td) {
		this.td = td;
	}


}