package com.cloudsolutionltd.cslMobileAccounts;

import junit.framework.TestCase;

/**
 * Created by csl on 7/21/16.
 */
public class FormatDateTest extends TestCase {

    private String expectedDate;
    private String actualDate;
    private String paramDate;
    private FormatDate formatDate = new FormatDate();

    public void testGetDateToShow() throws Exception {

        paramDate = "2016/07/21";
        actualDate = formatDate.getDateToShow(paramDate);
        expectedDate = "21/Jul/2016";
        assertEquals(expectedDate, actualDate);
    }

    public void testGetDateToSave() throws Exception {

        paramDate = "21/Jul/2016";
        actualDate = formatDate.getDateToSave(paramDate);
        expectedDate = "2016/07/21";
        assertEquals(expectedDate, actualDate);
    }
}