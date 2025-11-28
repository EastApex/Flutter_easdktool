//
//  EASocialSwitchModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//  File Name:21:Social Alert Switch【社交提醒开关】

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN

@class EASocialOps;


/**
 * id = 21
 * Social alert switch
 * 社交提醒开关
 *
 * Note a few things about testing your push:
 * 1. The watch must be paired with the phone to allow for notifications.
 * 2. The watch needs to disable DND
 * 3. Enable related Bluetooth protocols. Class EASocialSwitchModel and  Class  EAAppMessageSwitchData
 *
 */
@interface EASocialSwitchModel : EABaseModel

/// incoming call【来电】
@property(nonatomic, strong) EASocialOps *sIncomingcall;

/// Missed call【未知来电】
@property(nonatomic, strong) EASocialOps *sMissedcall;

/// Sms【短信】
@property(nonatomic, strong) EASocialOps *sSms;

/// Third - party App push reminder【第三方 App 推送提醒】
@property(nonatomic, strong) EASocialOps *sSocial;


/// Email【邮件】
@property(nonatomic, strong) EASocialOps *sEmail;


/// Calendar reminder【日历提醒】
@property(nonatomic, strong) EASocialOps *sSchedule;






/// Initialize【初始化】
/// - Parameters:
///  - remindActionType: Remind method【提醒方式】
///  - incomingcall: Incoming call reminder【来电提醒】
///  - missedcall: Missed call reminder【未接电话提醒】
///  - sms: SMS reminder【SMS 提醒】
///  - social: Third - party App push reminder【第三方 App 推送提醒】
///  - email: Email reminder【邮件提醒】
///  - schedule: Calendar reminder【日历提醒】
+(instancetype)eaInitWithRemindActionType:(EARemindActionType)remindActionType incomingcall:(NSInteger)incomingcall missedcall:(NSInteger)missedcall sms:(NSInteger)sms social:(NSInteger)social email:(NSInteger)email schedule:(NSInteger)schedule;

@end



@interface EASocialOps : EABaseModel

/// on-off【开关】
@property(nonatomic, assign) NSInteger sw;

/// Remind the way【提醒方式】
@property(nonatomic, assign) EARemindActionType remindActionType;


/// 初始化【Initialize】
/// - Parameters:
///  - onOff: 开关【Switch】
///  - remindActionType: 提醒方式【Remind method】
+(instancetype)eaInitWithOnOff:(NSInteger)onOff remindActionType:(EARemindActionType)remindActionType;

@end



NS_ASSUME_NONNULL_END
