package com.veio007.example.designpattern;

/**
 * [行为]
 * 迭代器
 * 观察者(发布订阅)
 * 策略 实现与使用分离，实现的改动与替换不影响使用者，类似面向接口的概念
 * 职责链  避免消息的请求者和接收者过度耦合，接收者自由组合功能链，每个单元处理完发送给下一个单元，类似jetty的handler功能
 * 命令 将调用封装为对象，解耦消息发送与接收者，类似远程调用的command对象
 * 中介者 消息的传递与调用通过中介者完成，消息传递是相互的
 * [结构]
 * 适配器 将不兼容的接口转换为兼容的接口，类似封装
 * 外观 子类(实现类)继承外观类(使用类)，使用者只需要关注外观类即可 => 用继承的方式实现比较重!接口的方式更轻巧
 * 代理	对类A的调用都通过代理类B来完成，和中介者的区别是代理是单向的
 * [创建]
 * 工厂方法 (简单工厂 类似switch case创建实例|多工厂 每个方法创建不同的实例|静态工厂 每个静态方法创建不同实例)
 * 抽象工厂 每个实现类对应一个工厂类，使用特定工厂类创建特定实现类 => 感觉不是很有必要
 * 单例
 * 建造者 和抽象工厂的区别是，将多个工厂类合成一个工厂类，这个复合的工厂类可以创建所有对象(不同方法)
 * 原型 复制和克隆对象产生新的类型对象
 */

public class patternTest {
}