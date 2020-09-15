/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iotdb.db.query.udf.api;

import java.util.List;
import java.util.Map;
import org.apache.iotdb.db.query.udf.api.collector.DataPointCollector;
import org.apache.iotdb.db.query.udf.api.customizer.config.UDTFConfigurations;
import org.apache.iotdb.db.query.udf.api.customizer.parameter.UDFParameters;
import org.apache.iotdb.db.query.udf.api.iterator.DataPointWindowIterator;
import org.apache.iotdb.db.query.udf.api.iterator.DataPointIterator;
import org.apache.iotdb.db.query.udf.api.iterator.Iterator;
import org.apache.iotdb.db.query.udf.api.iterator.OverallDataPointIterator;
import org.apache.iotdb.db.query.udf.api.iterator.OverallRowRecordIterator;
import org.apache.iotdb.db.query.udf.api.iterator.RowRecordWindowIterator;
import org.apache.iotdb.db.query.udf.api.iterator.RowRecordIterator;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Path;

public abstract class UDTF implements UDF {

  protected List<Path> paths;
  protected List<TSDataType> dataTypes;

  protected List<Iterator> dataPointIterators;
  protected Map<String, Iterator> rowRecordIterators;

  protected DataPointCollector collector;

  /**
   * @param parameters
   * @param configurations
   */
  public abstract void initializeUDF(UDFParameters parameters, UDTFConfigurations configurations);

  /**
   *
   */
  public abstract void transform() throws Exception;

  public final DataPointIterator getDataPointIterator(int index) {
    return (DataPointIterator) dataPointIterators.get(index);
  }

  public final DataPointWindowIterator getDataPointWindowIterator(int index) {
    return (DataPointWindowIterator) dataPointIterators.get(index);
  }

  public final OverallDataPointIterator getOverallDataPointIterator(int index) {
    return (OverallDataPointIterator) dataPointIterators.get(index);
  }

  public final RowRecordIterator getRowRecordIterator(String tabletName) {
    return (RowRecordIterator) rowRecordIterators.get(tabletName);
  }

  public final RowRecordWindowIterator getRowRecordWindowIterator(String tabletName) {
    return (RowRecordWindowIterator) rowRecordIterators.get(tabletName);
  }

  public final OverallRowRecordIterator getOverallRowRecordIterator(String tabletName) {
    return (OverallRowRecordIterator) rowRecordIterators.get(tabletName);
  }

  // The following methods are not for users

  public final void setPaths(List<Path> paths) {
    this.paths = paths;
  }

  public final void setDataTypes(List<TSDataType> dataTypes) {
    this.dataTypes = dataTypes;
  }

  public final void setDataPointIterators(List<Iterator> dataPointIterators) {
    this.dataPointIterators = dataPointIterators;
  }

  public final void setRowRecordIterators(Map<String, Iterator> rowRecordIterators) {
    this.rowRecordIterators = rowRecordIterators;
  }

  public final void setCollector(DataPointCollector collector) {
    this.collector = collector;
  }

  public final DataPointCollector getCollector() {
    return collector;
  }
}