package tech.powerjob.server.core.alarm.module;

import lombok.Data;
import lombok.experimental.Accessors;
import tech.powerjob.server.extension.alarm.Alarm;

import java.text.MessageFormat;

/**
 * 任务执行失败告警对象
 *
 * @author tjq
 * @since 2020/4/30
 */
@Data
@Accessors(chain = true)
public class JobInstanceAlarm implements Alarm {
    /**
     * 应用ID
     */
    private long appId;
    /**
     * 任务ID
     */
    private long jobId;
    /**
     * 任务实例ID
     */
    private long instanceId;
    /**
     *  任务名称
     */
    private String jobName;
    /**
     * 任务自带的参数
     */
    private String jobParams;
    /**
     *  时间表达式类型（CRON/API/FIX_RATE/FIX_DELAY）
     */
    private Integer timeExpressionType;
    /**
     * 时间表达式，CRON/NULL/LONG/LONG
     */
    private String timeExpression;
    /**
     * 执行类型，单机/广播/MR
     */
    private Integer executeType;
    /**
     * 执行器类型，Java/Shell
     */
    private Integer processorType;
    /**
     * 执行器信息
     */
    private String processorInfo;

    /**
     * 任务实例参数
     */
    private String instanceParams;
    /**
     * 执行结果
     */
    private String result;
    /**
     * 预计触发时间
     */
    private Long expectedTriggerTime;
    /**
     * 实际触发时间
     */
    private Long actualTriggerTime;
    /**
     * 结束时间
     */
    private Long finishedTime;
    /**
     *
     */
    private String taskTrackerAddress;

    @Override
    public String fetchTitle() {
        return "PowerJob任务监控告警";
    }

    @Override
    public String fetchContent() {
        return MessageFormat.format(loadEmailJobAlarmTemplate(),
                String.valueOf(appId),
                String.valueOf(jobId),
                jobName,
                jobParams,
                taskTrackerAddress,
                result
                );
    }

    /**
     * load email job alarm template
     *
     * @return
     */
    private static final String loadEmailJobAlarmTemplate(){
        return "<h5>监控告警明细：</span>" +
                "<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >\n" +
                "   <thead style=\"font-weight: bold;color: #ffffff;background-color: #ff8c00;\" >" +
                "      <tr>\n" +
                "         <td width=\"20%\" >appID</td>\n" +
                "         <td width=\"10%\" >任务ID</td>\n" +
                "         <td width=\"20%\" >任务名称</td>\n" +
                "         <td width=\"10%\" >执行参数</td>\n" +
                "         <td width=\"10%\" >worker地址</td>\n" +
                "         <td width=\"10%\" >告警类型</td>\n" +
                "         <td width=\"40%\" >任务执行结果</td>\n" +
                "      </tr>\n" +
                "   </thead>\n" +
                "   <tbody>\n" +
                "      <tr>\n" +
                "         <td>{0}</td>\n" +
                "         <td>{1}</td>\n" +
                "         <td>{2}</td>\n" +
                "         <td>{3}</td>\n" +
                "         <td>{4}</td>\n" +
                "         <td>任务执行失败</td>\n" +
                "         <td>{5}</td>\n" +
                "      </tr>\n" +
                "   </tbody>\n" +
                "</table>";
    }

}
