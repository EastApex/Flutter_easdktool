//
//  EAStocks.h
//  EABluetooth
//
//  Created by Aye on 2024/4/3.
//  File Name:64:Stocks【股票】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


@class EAStock;
/**
 * id = 64
 * Stocks
 * 股票
 */
@interface EAStocks : EABaseModel

/// max 10
@property(nonatomic, strong) NSMutableArray<EAStock *> *sStockArray;

/// 更新时间
@property(nonatomic, assign) NSInteger timestemp;

@end

/// Watch unit
typedef NS_ENUM(NSUInteger,EAStockColor) {
    
    EAStockColorUpRed = 0,
    EAStockColorUpGreen = 1,
};

/**
 * Stock
 * 股票
 */
@interface EAStock : EABaseModel

/// Stock code 16 bytes【股票代码      16字节】
@property(nonatomic, strong) NSString *stockId;

/// Stock name 32 bytes【股票名称        32字节】
@property(nonatomic, strong) NSString *stockName;

/// Stock price increase or decrease color type【股票涨跌颜色类型】
@property(nonatomic, assign) EAStockColor eStockColor;

/// Current price 16 bytes【当前价格        16字节】
@property(nonatomic, strong) NSString *stockPrice;

/// Price change percentage (%) 8 bytes【涨跌幅（%）    8字节】
@property(nonatomic, strong) NSString *stockPriceLimit;

@end


NS_ASSUME_NONNULL_END
