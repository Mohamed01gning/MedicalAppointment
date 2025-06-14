package com.medical.adminservice.Model;

import jakarta.validation.GroupSequence;

public class ValidationGroup
{
    interface group1{}
    interface group2{}
    interface group3{}
    interface group4{}

    @GroupSequence({group1.class,group2.class,group3.class,group4.class})
    public interface groupSequence{}
}
