package pcpnru.utilities;

import java.util.List;

public class Validate {
	public boolean CheckRegexNumberOnly(String textforCheck) {
		boolean resultcheck = false;
		if (textforCheck.equals("")) {
			resultcheck = true;
		} else {
			resultcheck = textforCheck.matches("\\d+$");
		}

		return resultcheck;
	}

	public boolean CheckListNotNull(List ListData) {
		boolean resultCheck = false;
		boolean checkListnotnull = false;
		if (ListData != null) {
			resultCheck = CheckListNotEmpty(ListData);
		}

		return resultCheck;
	}

	public boolean CheckListNotEmpty(List ListData) {
		boolean resultCheck = false;
		boolean checkListNotEmpty = false;
		if (!ListData.isEmpty()) {
			checkListNotEmpty = true;
		}

		return checkListNotEmpty;
	}

	public boolean Check_String_notnull_notempty(String value) {
		boolean resultcheck = false;
		if (value == null) {
			resultcheck = false;
		} else if (!value.equals("")) {
			resultcheck = true;
		}

		return resultcheck;
	}

	public boolean Check_String_notnull_notempty(String[] value) {
		boolean resultcheck = false;
		if (value == null) {
			resultcheck = false;
		} else if (!value.equals("")) {
			resultcheck = true;
		}
		return resultcheck;
	}
}
