package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.UseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.util.List;

@UseCase
public interface RequestScholarshipByStudentUseCase {
    public int start(List<String> information);
}
