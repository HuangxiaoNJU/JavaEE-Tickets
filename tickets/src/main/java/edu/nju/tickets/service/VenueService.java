package edu.nju.tickets.service;

import edu.nju.tickets.vo.*;

import java.util.List;

public interface VenueService {

    /**
     * 搜索场馆
     *
     * @param keywords          关键词
     * @return                  venue列表
     */
    List<VenueInfoVO> searchVenue(final String keywords);

    /**
     * 场馆注册
     *
     * @param vo                注册信息
     */
    void register(final VenueRegisterVO vo);

    /**
     * 场馆登录
     *
     * @param email             场馆邮箱
     * @param identification    识别码
     */
    boolean login(final String email, final String identification);

    /**
     * 审核场馆注册申请
     *
     * @param venueId           id
     * @param isPass            是否通过
     */
    void checkVenueRegister(final Integer venueId, final boolean isPass);

    /**
     * 审核场馆信息修改申请
     *
     * @param modifyId          修改记录id
     * @param isPass            是否通过
     */
    void checkVenueModify(final Integer modifyId, final boolean isPass);

    /**
     * 根据id获取venue信息
     *
     * @param id                id
     * @return                  venue信息
     */
    VenueInfoVO getVenueInfoById(final Integer id);

    /**
     * 根据识别码获取venue信息
     *
     * @param identification    识别码
     * @return                  venue信息
     */
    VenueInfoVO getVenueInfoByIdentification(final String identification);

    /**
     * 按制定顺序获取全部场馆
     *
     * @param property          排序属性
     * @param order             顺序（正序／逆序）
     * @return                  venue列表
     */
    List<VenueInfoVO> getVenues(final String property, final String order);

    /**
     * 申请修改场馆信息
     *
     * @param identification    识别码
     * @param vo                信息修改vo
     */
    void applyForChangeInfo(final String identification, final VenueChangeVO vo);

    /**
     * 获取申请注册场馆列表
     *
     * @param isChecked         是否被审核
     * @return                  venue列表
     */
    List<VenueInfoVO> getVenuesByIsChecked(boolean isChecked);

    /**
     * 获取场馆信息修改记录
     *
     * @param isChecked         是否被审核
     * @return                  venueModifyInfo列表
     */
    List<VenueModifyInfoVO> getVenueModifyInfo(final boolean isChecked);

    /**
     * 获取场馆统计信息
     *
     * @param identification    识别码
     * @return                  统计信息
     */
    VenueStatisticsVO getVenueStatistics(final String identification);

    /**
     * 获取所有场馆统计信息
     *
     * @return                  所有场馆统计信息
     */
    VenuesStatisticsVO getVenuesStatistics();

    /**
     * 获取场馆的平均评分
     *
     * @param id                id
     * @return                  场馆平均评分
     */
    Double getAverageScore(Integer id);

    /**
     * 获取场馆统计信息，与getVenueStatistics不同
     *
     * @param identification    识别码
     * @return                  统计信息
     */
    VenueIndividualStatistics getVenueIndividualStatistics(final String identification);

}
