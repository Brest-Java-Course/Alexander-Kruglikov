package com.brest.bank.dao;

import com.brest.bank.domain.BankDeposit;
import com.brest.bank.domain.BankDepositor;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.common.resources.Resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BankDepositorDaoImpl implements BankDepositorDao {

    public static final String ERROR_METHOD_PARAM = "The parameter can not be NULL";
    public static final String ERROR_NULL_PARAM = "The parameter must be NULL";
    public static final String ERROR_FROM_TO_PARAM = "The first parameter should be less than the second";

    private static final Logger LOGGER = LogManager.getLogger(BankDepositDaoImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private BankDepositor depositor;
    private List<BankDepositor> depositors;
    private BankDepositor param;
    private BankDeposit paramDeposit;
    private Map<String,Object> paramMap;

    @Autowired
    private SqlMapClient sqlMapClient;

    //private Reader reader;

    /*
    @PostConstruct
    public void init(){
        try{
            reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        }catch (IOException e){
            LOGGER.error("error - init() for BankDepositDaoImpl.class - {}", e.toString());
            throw new IllegalArgumentException("error - init() for BankDepositDaoImpl.class"+e.toString());
        }
        sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
    }
    */

    /**
     * Get all Bank Depositors
     *
     * @return List<BankDepositor> - a list containing all of the Bank Depositors in the database
     */
    @Override
    public List<BankDepositor> getBankDepositorsCriteria(){
        LOGGER.debug("getBankDepositorsCriteria()");
        depositors = new ArrayList<BankDepositor>();

        try{
            sqlMapClient.startTransaction();

            depositors = sqlMapClient.queryForList("BankDepositor.getAll",null);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - getBankDepositorsCriteria() - {}", e.toString());
            throw new IllegalArgumentException("error - getBankDepositorsCriteria()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - getBankDepositorsCriteria() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - getBankDepositorsCriteria() -> endTransaction -"+e.toString());
            }
        }
        LOGGER.debug("depositors-> {}",depositors);
        return depositors;
    }

    /**
     * Get Bank Depositor by ID
     *
     * @param depositorId  Long - id of the Bank Depositor to return
     * @return BankDepositor with the specified id from the database
     */
    @Override
    public BankDepositor getBankDepositorByIdCriteria(Long depositorId){
        LOGGER.debug("getBankDepositorsByIdCriteria(id={})",depositorId);
        Assert.notNull(depositorId,ERROR_METHOD_PARAM);
        depositor = new BankDepositor();

        try{
            sqlMapClient.startTransaction();

            param = new BankDepositor();
            param.setDepositorId(depositorId);

            depositor = (BankDepositor)sqlMapClient.queryForObject("BankDepositor.getById",param);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - getBankDepositorByIdCriteria(id = {}) - {}", depositorId,e.toString());
            throw new IllegalArgumentException("error - getBankDepositorByIdCriteria()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - getBankDepositorByIdCriteria() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - getBankDepositorByIdCriteria() -> endTransaction -"+e.toString());
            }
        }
        LOGGER.debug("depositor-> {}",depositor);
        return depositor;
    }

    /**
     * Get Bank Depositor by Name
     *
     * @param depositorName  String - name of the Bank Depositor to return
     * @return BankDepositor with the specified id from the database
     */
    @Override
    public BankDepositor getBankDepositorByNameCriteria(String depositorName){
        LOGGER.debug("getBankDepositorByNameCriteria(name: {})",depositorName);
        Assert.notNull(depositorName,ERROR_METHOD_PARAM);
        depositor = new BankDepositor();

        try{
            sqlMapClient.startTransaction();

            param = new BankDepositor();
            param.setDepositorName(depositorName);

            depositor = (BankDepositor)sqlMapClient.queryForObject("BankDepositor.getByName",param);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - getBankDepositorByNameCriteria(id = {}) - {}", depositorName,e.toString());
            throw new IllegalArgumentException("error - getBankDepositorByNameCriteria()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - getBankDepositorByNameCriteria() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - getBankDepositorByNameCriteria() -> endTransaction -"+e.toString());
            }
        }
        LOGGER.debug("depositor-> {}",depositor);
        return depositor;
    }

    /**
     * Get all Bank Depositors from-to Date Deposit
     *
     * @param start Date - start value of the date deposit (startDate < endDate)
     * @param end Date - end value of the date deposit (startDate < endDate)
     * @return List<BankDepositors> a list of all bank depositors with the specified task`s date deposit
     */
    @Override
    public List<BankDepositor> getBankDepositorsFromToDateDeposit(Date start, Date end){
        LOGGER.debug("getBankDepositorsFromToDateDeposit(start={}, end={}",dateFormat.format(start),dateFormat.format(end));
        Assert.notNull(start,ERROR_METHOD_PARAM);
        Assert.notNull(end,ERROR_METHOD_PARAM);
        Assert.isTrue(start.before(end),ERROR_FROM_TO_PARAM);

        try{
            sqlMapClient.startTransaction();

            paramMap = new HashMap<String, Object>();
                paramMap.put("startDate",start);
                paramMap.put("endDate",end);

            depositors = sqlMapClient.queryForList("BankDepositor.getFromToDateDeposit",paramMap);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - getBankDepositorsFromToDateDeposit({}, {}) - {}", dateFormat.format(start),
                    dateFormat.format(end),e.toString());
            throw new IllegalArgumentException("error - getBankDepositorsFromToDateDeposit()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - getBankDepositorsFromToDateDeposit() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - getBankDepositorsFromToDateDeposit() -> endTransaction -"+e.toString());
            }
        }
        LOGGER.debug("depositors-> {}",depositors);
        return depositors;
    }

    /**
     * Get all Bank Depositors from-to Date return Deposit
     *
     * @param start Date - start value of the date return deposit (startDate < endDate)
     * @param end Date - end value of the date return deposit (startDate < endDate)
     * @return List<BankDepositors> a list of all bank depositors with the specified task`s date return deposit
     */
    @Override
    public List<BankDepositor> getBankDepositorsFromToDateReturnDeposit(Date start, Date end){
        LOGGER.debug("getBankDepositorsFromToDateReturnDeposit(start={}, end={}",dateFormat.format(start),dateFormat.format(end));
        Assert.notNull(start,ERROR_METHOD_PARAM);
        Assert.notNull(end,ERROR_METHOD_PARAM);
        Assert.isTrue(start.before(end),ERROR_FROM_TO_PARAM);

        try{
            sqlMapClient.startTransaction();

            paramMap = new HashMap<String, Object>();
                paramMap.put("startDate",start);
                paramMap.put("endDate",end);

            depositors = sqlMapClient.queryForList("BankDepositor.getFromToDateReturnDeposit",paramMap);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - getBankDepositorsFromToDateReturnDeposit({}, {}) - {}", dateFormat.format(start),
                    dateFormat.format(end),e.toString());
            throw new IllegalArgumentException("error - getBankDepositorsFromToDateReturnDeposit()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - getBankDepositorsFromToDateReturnDeposit() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - getBankDepositorsFromToDateReturnDeposit() -> endTransaction -"+e.toString());
            }
        }
        LOGGER.debug("depositors-> {}",depositors);
        return depositors;
    }

    /**
     * Get Bank Depositors by ID Bank Deposit
     *
     * @param depositId  Long - id of the Bank Deposit
     * @return List<BankDepositor> with the specified id bank deposit from the database
     */
    @Override
    public List<BankDepositor> getBankDepositorByIdDepositCriteria(Long depositId){
        LOGGER.debug("getBankDepositorByIdDepositCriteria(id={})",depositId);
        Assert.notNull(depositId,ERROR_METHOD_PARAM);

        try{
            sqlMapClient.startTransaction();

            paramDeposit = new BankDeposit();
            paramDeposit.setDepositId(depositId);

            depositors = sqlMapClient.queryForList("BankDepositor.getByIdDeposit",paramDeposit);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - getBankDepositorByIdDepositCriteria(id={}) - {}", depositId, e.toString());
            throw new IllegalArgumentException("error - getBankDepositorByIdDepositCriteria()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - getBankDepositorByIdDepositCriteria() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - getBankDepositorByIdDepositCriteria() -> endTransaction -"+e.toString());
            }
        }
        LOGGER.debug("depositors-> {}",depositors);
        return depositors;
    }

    /**
     * Adding Bank Depositor
     *
     * @param depositId Long - id of the Bank Deposit
     * @param depositor BankDepositor - Bank Depositor to be inserted to the database
     */
    @Override
    @Transactional
    public void addBankDepositor(Long depositId, BankDepositor depositor){
        LOGGER.debug("addBankDepositor(id={}, depositor: {}",depositId,depositor);
        Assert.notNull(depositId,ERROR_METHOD_PARAM);
        Assert.notNull(depositor,ERROR_METHOD_PARAM);
        Assert.isNull(depositor.getDepositorId(),ERROR_NULL_PARAM);

        try{
            sqlMapClient.startTransaction();

            paramMap = new HashMap<String, Object>();
                paramMap.put("depositorId",depositor.getDepositorId());
                paramMap.put("depositorName",depositor.getDepositorName());
                paramMap.put("depositorDateDeposit",depositor.getDepositorDateDeposit());
                paramMap.put("depositorAmountDeposit",depositor.getDepositorAmountDeposit());
                paramMap.put("depositorAmountPlusDeposit",depositor.getDepositorAmountPlusDeposit());
                paramMap.put("depositorAmountMinusDeposit",depositor.getDepositorAmountMinusDeposit());
                paramMap.put("depositorDateReturnDeposit",depositor.getDepositorDateReturnDeposit());
                paramMap.put("depositorMarkReturnDeposit",depositor.getDepositorMarkReturnDeposit());
                paramMap.put("depositId",depositId);

            sqlMapClient.insert("BankDepositor.insert",paramMap);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - addBankDepositor({}, {}) - {}", depositId, depositor, e.toString());
            throw new IllegalArgumentException("error - addBankDepositor()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - addBankDepositor() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - addBankDepositor() -> endTransaction -"+e.toString());
            }
        }
    }

    /**
     * Updating Bank Depositor
     *
     * @param depositor BankDepositor - Bank Depositor to be stored in the database
     */
    @Override
    @Transactional
    public void updateBankDepositor(BankDepositor depositor){
        LOGGER.debug("updateBankDepositor({})",depositor);
        Assert.notNull(depositor,ERROR_METHOD_PARAM);
        try{
            sqlMapClient.startTransaction();

            sqlMapClient.update("BankDepositor.update",depositor);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - updateBankDepositor({}) - {}", depositor, e.toString());
            throw new IllegalArgumentException("error - updateBankDepositor()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - updateBankDepositor() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - updateBankDepositor() -> endTransaction -"+e.toString());
            }
        }
    }

    /**
     * Deleting Bank Depositor by ID
     *
     * @param id Long - id of the Bank Depositor to be removed
     */
    @Override
    @Transactional
    public void removeBankDepositor(Long id){
        LOGGER.debug("removeBankDepositor(id={})",id);
        Assert.notNull(id,ERROR_METHOD_PARAM);
        try{
            sqlMapClient.startTransaction();

            param = new BankDepositor();
            param.setDepositorId(id);

            sqlMapClient.delete("BankDepositor.delete",param);

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - removeBankDepositor({}) - {}", depositor, e.toString());
            throw new IllegalArgumentException("error - removeBankDepositor()"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - removeBankDepositor() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - removeBankDepositor() -> endTransaction -"+e.toString());
            }
        }
    }

    @Override
    public Integer rowCount(){
        LOGGER.debug("rowCount()");
        Integer count;
        try{
            sqlMapClient.startTransaction();

            count = (Integer) sqlMapClient.queryForObject("BankDepositor.rowCount");

            sqlMapClient.commitTransaction();
        }catch (Exception e){
            LOGGER.error("error - rowCount() - {}",e.toString());
            throw new IllegalArgumentException("error - rowCount():"+e.toString());
        }finally {
            try{
                sqlMapClient.endTransaction();
            }catch (SQLException e){
                LOGGER.error("error - rowCount() -> endTransaction -{}", e.toString());
                throw new IllegalArgumentException("error - rowCount() -> endTransaction -"+e.toString());
            }
        }
        LOGGER.debug("count-> {}",count);
        return count;
    }
}
