package com.aggregation;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author fede
 */
public class AggregationTest {

    @Test
    public void distinctCaseNames() throws Exception {
        int total = 10;

        Aggregation aggregation = new Aggregation("test", "interpretations", total, 2);
        aggregation.populateCollection();

        int actual = aggregation.distinctCaseNames();
        Assert.assertEquals(total, actual);
    }

    @Test
    public void distinctCaseNamesHuge() throws Exception {
        int total = 2_000_000;

        Aggregation aggregation = new Aggregation("test", "interpretations", total, 300);
        aggregation.populateCollection();

        int actual = aggregation.distinctCaseNames();
        Assert.assertEquals(total, actual);
    }

}
