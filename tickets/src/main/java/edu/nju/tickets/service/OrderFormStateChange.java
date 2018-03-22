package edu.nju.tickets.service;

public interface OrderFormStateChange {

    void convertNotPaidToCanceled();

    void convertNotAllocatedToFinished();

}
