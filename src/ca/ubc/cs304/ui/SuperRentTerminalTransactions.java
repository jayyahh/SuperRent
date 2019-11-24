package ca.ubc.cs304.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;

import ca.ubc.cs304.delegates.MainTerminalTransactionsDelegate;


/**
 * The class is only responsible for handling terminal text inputs.
 */
public class SuperRentTerminalTransactions {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;

    private BufferedReader bufferedReader = null;
    private MainTerminalTransactionsDelegate delegate = null;

    public SuperRentTerminalTransactions() {
    }

    /**
     * Displays simple text interface
     */
    public void showMainMenu(MainTerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println();
            System.out.println("1. View available cars to make a reservation");
            System.out.println("2. Rent a vehicle");
            System.out.println("3. Return a vehicle");
            System.out.println("4. Generate a daily report");
            System.out.println("5. Quit");
            System.out.print("Please choose one of the above 5 options: ");

            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        makeReservation();
                        break;
                    case 2:
                        rentVehicle();
                        break;
                    case 3:
                        returnVehicle();
                        break;
                    case 4:
                        generateDailyReport();
                        break;
                    case 5:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }

    private void makeReservation() {
        String carType = selectCarType();
        String location = selectLocation();
        Date startDate = selectDate("start");
        int startHour = selectHour();
        int startMin = selectMin();
        Time startTime = new Time (startHour, startMin, 0);
        Date endDate = selectDate("end");
        int endHour = selectHour();
        int endMin = selectMin();
        Time endTime = new Time (endHour, endMin, 0);
        if (isEndTimeLater(startDate, startHour, startMin, endDate, endHour, endMin)){
            delegate.showAvailableVehicles(carType, location, startDate, startTime, endDate, endTime);
        } else {
            System.out.println("End time must be later than start time, quitting program");
            handleQuitOption();
        }
    }

    private boolean isEndTimeLater(Date startDate, int startHour, int startMin, Date endDate, int endHour, int endMin) {
        if (startDate.getYear() > endDate.getYear()) {
            return false;
        } else if (startDate.getYear() == endDate.getYear()) {
            if (startDate.getMonth() > endDate.getMonth()) {
                return false;
            } else if (startDate.getMonth() == endDate.getMonth()) {
                if (startDate.getDay() > endDate.getDay()) {
                    return false;
                } else if (startDate.getDay() == endDate.getDay()) {
                    if (startHour > endHour) {
                        return false;
                    } else if (startHour == endHour) {
                        if (startMin >= endMin) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private int selectHour() {
        int choice = INVALID_INPUT;
        while (choice < 0 || choice > 23) {
            System.out.println("Valid hour: ");
            choice = readInteger(false);
        }
        return choice;
    }

    private int selectMin() {
        int choice = INVALID_INPUT;
        while (choice < 0 || choice > 59) {
            System.out.println("Valid minute: ");
            choice = readInteger(false);
        }
        return choice;
    }

    private Date selectDate(String time) {
        System.out.println("Please select " + time + " date: ");
        int year = selectYear();
        int month = selectMonth();
        int day = selectDay(month);
        return new Date(year, month, day);
    }

    private int selectYear() {
        int choice = INVALID_INPUT;
        while (choice < 2019 || choice > 2030) {
            System.out.println("Valid year between 2019 and 2030: ");
            choice = readInteger(false);
        }
        return choice;
    }

    private int selectMonth() {
        int choice = INVALID_INPUT;
        while (choice < 1 || choice > 12) {
            System.out.println("Valid month: ");
            choice = readInteger(false);
        }
        return choice;
    }

    private int selectDay(int month){
        int choice = INVALID_INPUT;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            while (choice < 1 || choice > 31) {
                System.out.println("Valid day of month " + month + " : ");
                choice = readInteger(false);
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            while (choice < 1 || choice > 30) {
                System.out.println("Valid day of month " + month + " : ");
                choice = readInteger(false);
            }
        } else {
            while (choice < 1 || choice > 28) {
                System.out.println("Valid day of month" + month + " : ");
                choice = readInteger(false);
            }
        }
        return choice;
    }

    private String selectLocation() {
        String location = "";
        int choice = INVALID_INPUT;
        System.out.println("Please select one of the following locations: ");
        System.out.println("1. Cambie, Vancouver");
        System.out.println("2. Oak, Vancouver");
        System.out.println("3. Granville, Vancouver");
        System.out.println("4. SunnyCoast, Whistler");
        System.out.println("5. Bridgeport, Richmond");
        System.out.println("6. Bay, Seattle");
        System.out.println("7. Quit");
        while (choice < 0 || choice > 7) {
            choice = readInteger(true);
            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 0:
                        location = "Any";
                        break;
                    case 1:
                        location = "Cambie";
                        break;
                    case 2:
                        location = "Oak";
                        break;
                    case 3:
                        location = "Granville";
                        break;
                    case 4:
                        location = "SunnyCoast";
                        break;
                    case 5:
                        location = "Bridgeport";
                        break;
                    case 6:
                        location = "Bay";
                        break;
                    case 7:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
        return location;
    }

    private String selectCarType() {
        String carType = "";
        int choice = INVALID_INPUT;
        System.out.println();
        System.out.println("Please select one of the following car types: ");
        System.out.println("1. Economy");
        System.out.println("2. Compact");
        System.out.println("3. Mid-size");
        System.out.println("4. Standard");
        System.out.println("5. Full-size");
        System.out.println("6. SUV");
        System.out.println("7. Truck");
        System.out.println("8. Quit");
        while (choice < 0 || choice > 8) {
            choice = readInteger(true);
            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 0:
                        carType = "Any";
                        break;
                    case 1:
                        carType = "Economy";
                        break;
                    case 2:
                        carType = "Compact";
                        break;
                    case 3:
                        carType = "Mid-size";
                        break;
                    case 4:
                        carType = "Standard";
                        break;
                    case 5:
                        carType = "Full-size";
                        break;
                    case 6:
                        carType = "SUV";
                        break;
                    case 7:
                        carType = "Truck";
                        break;
                    case 8:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
        return carType;
    }

    private void rentVehicle() {
        delegate.rentVehicle();
    }

    private void returnVehicle() {
        delegate.returnVehicle();
    }

    private void generateDailyReport() {
        int choice = INVALID_INPUT;
        while (choice != 5) {
            System.out.println();
            System.out.println("1. Report of all daily rentals");
            System.out.println("2. Report of daily rentals by branch");
            System.out.println("3. Report of all daily returns");
            System.out.println("4. Report of daily returns by branch");
            System.out.println("5. Quit");
            System.out.print("Please choose one of the above 5 options: ");
            choice = readInteger(false);
            System.out.println(" ");
            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        Date date1 = selectDate("report");
                        delegate.generateRentalsReport(date1);
                        break;
                    case 2:
                        Date date2 = selectDate("report");
                        String branch1 = selectLocation();
                        delegate.generateRentalsBranchReport(date2, branch1);
                        break;
                    case 3:
                        Date date3 = selectDate("report");
                        delegate.generateReturnsReport(date3);
                        break;
                    case 4:
                        Date date4 = selectDate("report");
                        String branch2 = selectLocation();
                        delegate.generateReturnsBranchReport(date4, branch2);
                        break;
                    case 5:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }

    private void handleQuitOption() {
        System.out.println("Good Bye!");

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("IOException!");
            }
        }

        delegate.terminalTransactionsFinished();
    }

    private int readInteger(boolean allowEmpty) {
        String line = null;
        int input = INVALID_INPUT;
        try {
            line = bufferedReader.readLine();
            input = Integer.parseInt(line);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        } catch (NumberFormatException e) {
            if (allowEmpty && line.length() == 0) {
                input = EMPTY_INPUT;
            } else {
                System.out.println(WARNING_TAG + " Your input was not an integer");
            }
        }
        return input;
    }

    private String readLine() {
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }
}