package com.gcit.library.entity;

public class LibraryBranch implements BaseEntity{
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	public LibraryBranch(){}
	
	public LibraryBranch(int branchId) {
		super();
		this.branchId = branchId;
	}

	public LibraryBranch(String branchName, String branchAddress) {
		this.branchName = branchName;
		this.branchAddress = branchAddress;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
}
