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


    @Test
    public void project1PrintsReadme() {
        this.project1.printReadMe();
    }

    @Test
    public void project1PrintsFlight() {
        Flight flight = new Flight(42, "pdx", "3/15/2017 10:39", "AMS", "3/16/2017 9:25");
        this.project1.print(flight);
    }


}
