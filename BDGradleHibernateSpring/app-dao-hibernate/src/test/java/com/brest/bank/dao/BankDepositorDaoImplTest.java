package com.brest.bank.dao;

import com.brest.bank.domain.BankDeposit;
import com.brest.bank.domain.BankDepositor;
import com.brest.bank.dao.HibernateUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.criterion.Projections;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
public class BankDepositorDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String ERROR_EMPTY_BD = "Data Base is empty";
    private static final String ERROR_SIZE = "Size can not be 0";
    private static final String ERROR_NULL = "The parameter can not be NULL";
    Object result;
    Integer sizeBefore = 0, sizeAfter = 0;

    @Autowired
    private BankDepositorDao depositorDao;
    @Autowired
    private BankDepositDao depositDao;

    private BankDeposit deposit;
    private BankDepositor depositor;
    private List<BankDepositor> depositors;

    @Before
    public void setUp() throws Exception {
        depositor = new BankDepositor();
        depositors = new ArrayList<BankDepositor>();
    }

    @Test
    public void testGetBankDepositorsCriteria() throws Exception {
        depositors = depositorDao.getBankDepositorsCriteria();
        LOGGER.debug("depositors.size()= {}", depositors.size());

        assertFalse(depositors.isEmpty());
        assertNotNull(depositors);
    }

    @Test
    public void testGetBankDepositorsFromToDateDeposit() throws ParseException{
        Date startDate = dateFormat.parse("2015-11-02");
        Date endDate = dateFormat.parse("2015-12-04");

        depositors = depositorDao.getBankDepositorsFromToDateDeposit(startDate,endDate);
        LOGGER.debug("depositors.size()= {}", depositors.size());

        assertFalse(depositors.isEmpty());
        assertNotNull(depositors);
    }

    @Test
    public void testGetBankDepositorsFromToDateReturnDeposit() throws ParseException{
        Date startDate = dateFormat.parse("2015-11-02");
        Date endDate = dateFormat.parse("2015-12-04");

        depositors = depositorDao.getBankDepositorsFromToDateReturnDeposit(startDate, endDate);
        LOGGER.debug("depositors.size()= {}", depositors.size());

        assertFalse(depositors.isEmpty());
        assertNotNull(depositors);
    }

    @Test
    public void testGetBankDepositorByIdCriteria() throws Exception {
        depositor = depositorDao.getBankDepositorByIdCriteria(1L);
        LOGGER.debug("depositor = {}", depositor);

        assertEquals("BankDepositor: { depositorId=1, depositorName=depositorName1, depositorDateDeposit=2015-12-01, " +
                "depositorAmountDeposit=1001, depositorAmountPlusDeposit=20, depositorAmountMinusDeposit=20, " +
                "depositorDateReturnDeposit=2015-12-02, depositorMarkReturnDeposit=0}",depositor.toString());
    }

    @Test
    public void testGetBankDepositorByIdDepositCriteria() throws Exception {
        depositors = depositorDao.getBankDepositorByIdDepositCriteria(1L);
        LOGGER.debug("depositors = {}", depositors);

        assertFalse(ERROR_EMPTY_BD,depositors.isEmpty());
        assertThat(ERROR_SIZE,depositors.size(), is(not(0)));
        assertNotNull(ERROR_NULL,depositors);
    }

    @Test
    public void testGetBankDepositorByNameCriteria() throws Exception {
        depositor = depositorDao.getBankDepositorByNameCriteria("depositorName1");
        LOGGER.debug("depositor = {}", depositor);

        assertEquals("BankDepositor: { depositorId=1, depositorName=depositorName1, depositorDateDeposit=2015-12-01, " +
                "depositorAmountDeposit=1001, depositorAmountPlusDeposit=20, depositorAmountMinusDeposit=20, " +
                "depositorDateReturnDeposit=2015-12-02, depositorMarkReturnDeposit=0}",depositor.toString());
    }

    @Test
    public void testAddBankDepositor() throws Exception {
        depositor = new BankDepositor();
            depositor.setDepositorName("newName");
            depositor.setDepositorDateDeposit(dateFormat.parse("2015-12-02"));
            depositor.setDepositorAmountDeposit(1000);
            depositor.setDepositorAmountPlusDeposit(10);
            depositor.setDepositorAmountMinusDeposit(10);
            depositor.setDepositorDateReturnDeposit(dateFormat.parse("2015-12-03"));
            depositor.setDepositorMarkReturnDeposit(0);
        LOGGER.debug("add new depositor - {}", depositor);

        sizeBefore = rowCount(BankDepositor.class);
        LOGGER.debug("size before add = {}", sizeBefore);

        depositorDao.addBankDepositor(1L, depositor);

        assertTrue(depositor.getDepositorId() != null);
        assertEquals(depositor.toString(), depositorDao.getBankDepositorByIdCriteria(depositor.getDepositorId()).toString());

        sizeAfter = rowCount(BankDepositor.class);
        LOGGER.debug("size after add = {}", sizeAfter);

        assertTrue(sizeAfter == sizeBefore+1);
    }

    /**
     * Get count rows in the DB
     * @param name - Name of the Class entity
     * @return Integer - count rows entity with the Class name
     * @throws ClassNotFoundException
     */
    public Integer rowCount(Class<?> name) throws ClassNotFoundException{
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        //--- query
        result = HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(name)
                .setProjection(Projections.rowCount()).uniqueResult();

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return Integer.parseInt(result.toString());
    }
}