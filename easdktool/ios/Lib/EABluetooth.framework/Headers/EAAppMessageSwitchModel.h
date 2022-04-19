//
//  EAAppMessageSwitchModel.h
//  EABluetooth
//
//  Created by Aye on 2021/9/18.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN




@interface EAAppMessageSwitchModel : EABaseModel
/**
 unknow=0;        //类型：其他社交类型
 wechat =1;
 qq=2;
 facebook=3;
 twitter = 4;
 messenger =5;
 hangouts =6;
 gmail = 7;
 viber=8;
 snapchat=9;
 whatsApp=10;
 instagram =11;
 linkedin =12;
 line =13;
 skype =14;
 booking =15;
 airbnb =16;
 flipboard =17;
 spotify =18;
 pandora =19;
 telegram =20;
 dropbox =21;
 waze =22;
 lift =23;
 slack =24;
 shazam =25;
 deliveroo =26;
 kakaotalk =27;
 pinterest =28;
 tumblr =29;
 vk =30;
 youtube=31;
 */
@property(nonatomic, assign) BOOL sw;



@end


@interface EAAppMessageSwitchData : EABaseModel

@property(nonatomic,strong) NSMutableArray<EAAppMessageSwitchModel*> *sAppSwArray;

/// MARK: - 
+ (EAAppMessageSwitchData *)getModelByData:(NSData *)data;


/// MARK: - 获取提醒事件操作设置数据流
- (NSData *)getModelData ;

@end

NS_ASSUME_NONNULL_END
