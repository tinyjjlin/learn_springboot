<template>
  <div class="app-container">
    <!-- <el-row type="flex" class="row-bg query-wrap"> -->
      <!-- 员工查找 -->
      <!-- <div class="block right-offset">
        <el-input prefix="员工" type="number" label="员工" 
        v-model="selectStaff" placeholder="员工工号"></el-input> -->
        <!--
          <el-select v-model="value" placeholder="请选择" >
             <el-option
               v-for="item in selectStaff"
               :key="item.value"
               :label="item.label"
               :value="item.value">
             </el-option>
            </el-select>
        -->
      <!-- </div> -->
      <!-- 选择时间范围 -->
      <!-- <div class="block right-offset">
        <span>时间范围</span>
        <el-date-picker
          v-model="selectTimeSpace"
          value-format="yyyy-MM-dd"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions2"
        ></el-date-picker>
      </div> -->
      <!-- 查找按钮 -->
      <!-- <el-button
        v-waves
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >{{ $t('table.search') }}</el-button> -->
      <!-- 数据导出按钮 -->
      <!-- <el-button
        v-waves
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >{{ $t('table.export') }}</el-button>
    </el-row> -->

   <!-- <div>
      <el-table :data="tableData" style="width: 100%"> -->
        <!-- <el-table-column
          v-for="(col) in cols"
          :key="col.fieldNo"
          :label="col.fieldDesc"
          :prop="col.fieldName"
          v-if="col.isEnabled===1"
          align="center"
        ></el-table-column>
      </el-table>
    </div>
    <pagination
      v-show="total"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="handleFilter"
    /> -->
  </div>
</template>

<script>
import { salaryReport } from "@/api/salary";
import waves from "@/directive/waves"; // Waves directive
import { parseTime } from "@/utils";
import Pagination from "@/components/Pagination"; // Secondary package based on el-pagination
import { resolve } from 'q';

export default {
  name: "stafflist",
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      tableData: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: "+id"
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [
        { label: "ID Ascending", key: "+id" },
        { label: "ID Descending", key: "-id" }
      ],
      statusOptions: ["published", "draft", "deleted"],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: "",
        timestamp: new Date(),
        title: "",
        type: "",
        status: "published"
      },
      textMap: {
        update: "Edit",
        create: "Create"
      },
      downloadLoading: false,
      pickerOptions2: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      },
      selectStaff: [],
      selectTimeSpace: [],
      cols: []
    };
  },
  created() {
    //  this.getList()
  },
  methods: {
    handleFilter() {
      // let queryParams = {
      //   staffId: this.selectStaff,
      //   startTime: this.selectTimeSpace[0],
      //   endTime: this.selectTimeSpace[1],
      //   page: this.listQuery.page,
      //   limit: this.listQuery.limit
      // };
      // salaryReport(queryParams).then(response => {
      //   this.cols = response.data.data.columnList;
      //   this.tableData = response.data.data.rowList.list;
      //   this.total = response.data.data.rowList.total;
      // });
    },
    handleDownload() {}
  }
};
</script>



<style rel="stylesheet/scss" lang="scss">
.query-wrap {
  margin-bottom: 20px;
}

.right-offset {
  margin-right: 10px;
}
</style>