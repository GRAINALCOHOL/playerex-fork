# PlayerEX 酒精版

| >**简体中文**< | [**English**](README-en.md) |

## 做了什么？

1. 降低暴击伤害属性强度
   - 修改远程暴击伤害计算公式
      - 原公式为`damage * (1.0 + (10.0 * v))`，其中`v`是当前远程暴击伤害数值，其在计算时会被乘以10倍，这导致了每提高10%的远程暴击伤害就会增加1倍伤害
      - 修改后的公式为`damage * (1.0 + v)`，避免在多模组或整合包环境中伤害提升太快
   - 修改近程暴击伤害计算公式
      - 原公式为`damage * (1.5 + v)`，其中`v`是当前近程暴击伤害数值
      - 修改后为`damage * (1.0 + v)`，与上文提到的远程暴击伤害计算方式相同
2. 完善本地化
   - 补全UI中遗漏的本地化
      - 具体是哪里的文案，如下图
      - ![展示](https://s2.loli.net/2025/06/02/ajRNJfgmtz2sdnZ.png)
      - [查看en_us](src/main/resources/assets/playerex/lang/en_us.json#L259)
      - [查看zh_cn](src/main/resources/assets/playerex/lang/zh_cn.json#L83)
   - 新增中文本地化
      - 但是并没有翻译完……
      - [查看本地化文件](src/main/resources/assets/playerex/lang/zh_cn.json)
