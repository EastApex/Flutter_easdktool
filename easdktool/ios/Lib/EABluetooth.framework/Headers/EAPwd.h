//
//  EAPwd.h
//  EABluetooth
//
//  Created by Aye on 2024/4/3.
//  File Name:65:Watch Password【手表密码】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 65
 * watch password
 * 手表密码
 */
@interface EAPwd : EABaseModel

@property(nonatomic, assign) NSInteger sw;

@property(nonatomic, strong) NSString *pwd;


@end

NS_ASSUME_NONNULL_END
