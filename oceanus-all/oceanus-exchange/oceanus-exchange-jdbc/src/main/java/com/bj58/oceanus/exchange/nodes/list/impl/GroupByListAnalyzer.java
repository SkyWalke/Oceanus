/*
 *  Copyright Beijing 58 Information Technology Co.,Ltd.
 *
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.bj58.oceanus.exchange.nodes.list.impl;

import com.bj58.oceanus.core.shard.AnalyzeResult;
import com.bj58.oceanus.core.shard.TableColumn;
import com.bj58.oceanus.exchange.nodes.AbstractNodeAnalyzer;
import com.bj58.oceanus.exchange.nodes.DefaultAnalyzeResult;
import com.bj58.sql.parser.ColumnReference;
import com.bj58.sql.parser.GroupByColumn;
import com.bj58.sql.parser.GroupByList;
import com.bj58.sql.parser.NodeTypes;
import com.bj58.sql.parser.ValueNode;

/**
 * SQL节点解析器:GroupByListAnalyzer
 * 
 * @author Service Platform Architecture Team (spat@58.com)
 */
public class GroupByListAnalyzer extends
		AbstractNodeAnalyzer<GroupByList, AnalyzeResult> {
	int[] nodeTypes = { NodeTypes.GROUP_BY_LIST };

	@Override
	public int[] getNodeTypes() {
		return nodeTypes;
	}

	@Override
	public AnalyzeResult doAnalyze(GroupByList node) {
		DefaultAnalyzeResult result = new DefaultAnalyzeResult();
		for (GroupByColumn column : node) {
			ValueNode valueNode = column.getColumnExpression();
			if (valueNode instanceof ColumnReference) {
				ColumnReference colRef = (ColumnReference) valueNode;
				TableColumn tableColumn = new TableColumn();
				tableColumn.setName(colRef.getColumnName());
				tableColumn.setSchema(colRef.getSchemaName());
				tableColumn.setTable(colRef.getTableName());
				result.getGroupByColumns().add(tableColumn);
			}
		}
		return result;
	}

}
