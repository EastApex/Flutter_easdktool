//
//  EAFastGetSnNumber.h
//  EABluetooth
//
//  Created by Aye on 2022/9/22.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class CBPeripheral;



//@protocol EAFastGetSnNumberManagerDelegate <NSObject>
//
//
//- (void)getSnNumberManager:(CBPeripheral *)peripheral snNumber:(NSString *)snNumber;
//
//@end

@interface EAFastGetSnNumberManager : NSObject


//@property(nonatomic,weak) id<EAFastGetSnNumberManagerDelegate> delegate;


+ (instancetype)defaultManager;


- (NSDictionary *)getSnNumbers;

@end

NS_ASSUME_NONNULL_END
