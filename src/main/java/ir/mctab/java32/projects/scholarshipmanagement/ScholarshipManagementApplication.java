package ir.mctab.java32.projects.scholarshipmanagement;

import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl.*;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.*;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl.LoginUseCaseImpl;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl.LogupUseCaseImpl;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LoginUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LogupUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.Scholarship;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScholarshipManagementApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!command.equals("exit")) {
            talk(AuthenticationService.getInstance().getLoginUser());
            command = scanner.nextLine();
            // Login
            if (command.equals("login")) {
                System.out.println("Username : ");
                String username = scanner.nextLine();
                System.out.println("Password : ");
                String password = scanner.nextLine();
                LoginUseCase loginUseCase = new LoginUseCaseImpl();
                String date = "1397-09-21";
                User user = loginUseCase.login(username, password, date);
                if (user != null) {
                    System.out.println("Login successful by " + user.getRole());
                } else {
                    System.out.println("Login Failed");
                }
            }
            //logup
            if (command.equals("logup")) {
                System.out.println("Username : ");
                String username = scanner.nextLine();
                System.out.println("Password : ");
                String password = scanner.nextLine();
                LogupUseCase logupUseCase = new LogupUseCaseImpl();
                String date = "1397-09-21";
                User user = logupUseCase.logup(username, password, date);
                if (user != null) {
                    System.out.println("logup and Login successful by " + user.getRole());
                } else {
                    System.out.println("Logup Failed");
                }
            }
            //logout
            if (command.equals("logout")) {
                LogoutUseCase logoutUseCase = new ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl.LogoutUseCase();
                logoutUseCase.logout();
                if (AuthenticationService.getInstance().getLoginUser() == null) {
                    System.out.println("Logout successful");
                }
            }

            // find scholarship by supervisort
            if (command.equals("svlist")) {
                FindScholarshipBySupervisorUseCase findScholarshipBySupervisorUseCase
                        = new FindScholarshipBySupervisorUseCaseImpl();

                List<Scholarship> scholarships = findScholarshipBySupervisorUseCase
                        .listScholarships();
                for (int i = 0; i < scholarships.size(); i++) {
                    System.out.println(scholarships.get(i));
                }
            }

            // accept scholarship by supervisor
            if (command.equals("svaccept")) {
                AcceptScholarshipBySupervisorUseCase acceptScholarshipBySupervisorUseCase
                        = new AcceptScholarshipBySupervisorUseCaseImpl();
                System.out.println("Scholarship Id: ");
                String scholarshipId = scanner.nextLine();
                int done = acceptScholarshipBySupervisorUseCase.accept(Long.parseLong(scholarshipId));
                if (done != 0) {
                    System.out.println("Done.");
                }
            }

            //reject scholarship by supervisor
            if (command.equals("svreject")) {
                RejectScholarshipBySupervisorUseCase rejectScholarshipBySupervisorUseCase
                        = new RejectScholarshipBySupervisorUseCaseImpl();
                System.out.println("Scholarship Id: ");
                String scholarshipId = scanner.nextLine();
                int done = rejectScholarshipBySupervisorUseCase.reject(Long.parseLong(scholarshipId));
                if (done != 0) {
                    System.out.println("Done.");
                }
            }

            // find scholarship by manager
            if (command.equals("mnlist")) {
                FindScholarshipByManagerUseCase findScholarshipByManagerUseCase
                        = new FindScholarshipByManagerUseCaseImpl();

                List<Scholarship> scholarships = findScholarshipByManagerUseCase
                        .listScholarships();
                for (int i = 0; i < scholarships.size(); i++) {
                    System.out.println(scholarships.get(i));
                }
            }

            // accept scholarship by manager
            if (command.equals("mnaccept")) {
                AcceptScholarshipByManagerUseCase acceptScholarshipByManagerUseCase
                        = new AcceptScholarshipByManagerUseCaseImpl();
                System.out.println("Scholarship Id: ");
                String scholarshipId = scanner.nextLine();
                acceptScholarshipByManagerUseCase.acceptByManager(Long.parseLong(scholarshipId));

            }

            // Dashboard scholarship by manager
            if (command.equals("dashboard")) {
                DashboardScholarshipByManagerUseCase dashboardScholarshipByManagerUseCase
                        = new DashboardScholarshipByManagerUseCaseImpl();
                System.out.println(dashboardScholarshipByManagerUseCase.listScholarshipsStatus());
            }

            //reject scholarship by manager
            if (command.equals("mnreject")) {
                RejectScholarshipByManagerUseCase rejectScholarshipByManagerUseCase
                        = new RejectScholarshipByManagerUseCaseImpl();
                System.out.println("Scholarship Id: ");
                String scholarshipId = scanner.nextLine();
                rejectScholarshipByManagerUseCase.rejectByManager(Long.parseLong(scholarshipId));
                System.out.println("Done");
            }

            // find scholarship by university
            if (command.equals("unilist")) {
                FindScholarshipByUniversityUseCase findScholarshipByUniversityUseCase
                        = new FindScholarshipByUniversityUseCaseImpl();

                List<Scholarship> scholarships = findScholarshipByUniversityUseCase
                        .listScholarships();
                for (int i = 0; i < scholarships.size(); i++) {
                    System.out.println(scholarships.get(i));
                }
            }

            // request scholarship by student
            if (command.equals("request")) {
                System.out.println("Please Enter your Data Like This:" +
                        "name, family, nationalCode, lastUni, lastDegree, lastField, lastScore, applyUni, applyDegree, applyField, applyDate");
                String preInfo = scanner.nextLine();
                List<String> info = Arrays.asList(preInfo.split(Pattern.quote(", ")));
                if (info.size() == 11) {
                    RequestScholarshipByStudentUseCase requestScholarshipByStudentUseCase
                            = new RequestScholarshipByStudentUseCaseImpl();

                    int status = requestScholarshipByStudentUseCase
                            .start(info);
                    if (status == 1) {
                        System.out.println("Your request submitted");
                    }
                }
            }

            // find scholarship by student
            if (command.equals("stlist")) {
                FindScholarshipByStudentUseCase findScholarshipByStudentUseCase
                        = new FindScholarshipByStudentUseCaseImpl();

                List<Scholarship> scholarships = findScholarshipByStudentUseCase
                        .listScholarships();
                for (int i = 0; i < scholarships.size(); i++) {
                    System.out.println(scholarships.get(i));
                }
            }

            // Dashboard scholarship by student
            if (command.equals("stdashboard")) {
                DashboardScholarshipByStudentUseCase dashboardScholarshipByStudentUseCase
                        = new DashboardScholarshipByStudentUseCaseImpl();
                System.out.println(dashboardScholarshipByStudentUseCase.listScholarshipsStatus());
            }

        }
    }

    static void talk(User user) {
        System.out.println("What do you want to do?");
        if (user == null) {
            System.out.println("options: \nlogup\tlogin\texit");
        } else {
            if (user.getRole().equals("Student")) {
                System.out.println("options: \nrequest\tstlist\tstdashboard\texit\tlogout");
            }
            if (user.getRole().equals("Manager")) {
                System.out.println("options: \nmnaccept\tmnreject\tmnlist\tdashboard\texit\tlogout");
            }
            if (user.getRole().equals("Supervisor")) {
                System.out.println("options: \nsvaccept\tsvreject\tsvlist\texit\tlogout");
            }
            if (user.getRole().equals("University")) {
                System.out.println("options: unilist\texit\tlogout");
            }
        }
    }
}

//masod, tavasoli, 85862, shahed, bachelor, civil, 18, sharif, MA, economic, 1399-03-07