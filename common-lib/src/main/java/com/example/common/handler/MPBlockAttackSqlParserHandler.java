package com.example.common.handler;

import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import net.sf.jsqlparser.statement.delete.Delete;
import org.springframework.stereotype.Component;

/**
 * 攻击 SQL 阻断解析器
 * 阻止恶意的全表更新删除
 *
 * @author Leo
 * @date 2020.04.26
 */
@Component
public class MPBlockAttackSqlParserHandler extends BlockAttackSqlParser {

	@Override
	public void processDelete(Delete delete) {
		// 如果你想自定义做点什么，可以重写父类方法像这样子
//                if ("user".equals(delete.getTable().getName())) {
//                    // 自定义跳过某个表，其他关联表可以调用 delete.getTables() 判断
//                    return ;
//                }
		super.processDelete(delete);
	}
}
