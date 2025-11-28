//
//  EASos.h
//  EABluetooth
//
//  Created by Aye on 2023/6/29.
//  File Name:58:Sos【紧急联系人】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN
/**
 * id = 58
 * 紧急联系人
 * Sos
 */
@interface EASos : EABaseModel
/// name【名称】
@property(nonatomic, strong) NSString *name;

/// number【号码】
@property(nonatomic, strong) NSString *num;

@property(nonatomic, strong) NSString *nameEx1 __attribute__((deprecated));
@property(nonatomic, strong) NSString *numEx1 __attribute__((deprecated));

@property(nonatomic, strong) NSString *nameEx2 __attribute__((deprecated));
@property(nonatomic, strong) NSString *numEx2 __attribute__((deprecated));


@end

NS_ASSUME_NONNULL_END
