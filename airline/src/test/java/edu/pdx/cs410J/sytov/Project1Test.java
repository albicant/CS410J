package edu.pdx.cs410J.sytov;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Project1} class.
 */

public class Project1Test {

    public Project1 project1 = new Project1();

//    @Ignore
//    @Test (expected = IllegalArgumentException.class)
//    public void project1ExitsParsingIncorrectInput() {
//        String test1 = "Delta f55 PDX 1/22/2020 17:30 AMS 01/23/2020 19:00 -print";
//        String[] args = test1.split(" ");
//        this.project1.main(args);
//    }

    @Test
    public void project1PrintsReadme() {
        this.project1.printReadMe();
    }


}
