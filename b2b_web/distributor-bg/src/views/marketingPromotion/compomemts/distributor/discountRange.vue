<template>
  <div>
    <el-form-item label="折扣范围:" style="font-weight: 500;">
      <el-radio-group v-model="formData.distributorType">
        <div class="dis-item1">
          <el-radio :label="1" :disabled="disabled">全部分销商</el-radio>
        </div>

        <div class="dis-item2">
          <div>
            <el-radio :label="2" :disabled="disabled">指定分销商等级</el-radio>
          </div>
          <el-col v-if="formData.distributorType === 2">
            <el-form-item>
              <el-checkbox-group v-model="formData.distributorGradeIds" :disabled="disabled">
                <el-checkbox v-for="item in distributorList" :label="item.id" :key="item.id" :value="item.id">{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </div>

        <div class="dis-item3">
          <el-radio :label="3" :disabled="disabled">指定分销商</el-radio>
          <div class="distributor-content" v-if="formData.distributorType ===3">
            <el-button class="mini-search-btn" icon="el-icon-plus" @click="distributorShow=true" v-if="!disabled" :disabled="disabled"> 添加分销商 </el-button>
            <el-table class="tableCenter goods-table" :data="formData.distributorData" border header-row-class-name="header-row" style="width: 100%" max-height="300">
              <el-table-column align="center" label="分销商用户名" width="150" prop="name"></el-table-column>
              <el-table-column align="center" label="公司名" show-overflow-tooltip width="300" prop="companyName"></el-table-column>
              <el-table-column align="center" label="操作" width="80">
                <template slot-scope="scope" v-if="!disabled">
                  <el-button style="margin-top:0px;margin-bottom:0px;" class="mini-delete-btn" @click="handleDeleteDistributor(scope.$index)" :disabled="disabled"> 删除 </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-radio-group>
    </el-form-item>
    <el-dialog :modal-append-to-body="false" :visible="distributorShow" :before-close="disCancel" width="80%">
      <select-distributor :distributorData="formData.distributorData"  ref="selectDistributor" @cancel="cancel" @submit="disSubmit"> </select-distributor>
    </el-dialog>
  </div>
</template>
<script>
import selectDistributor from "@/views/goods/components/selectDistributor"
export default {
  props: ['type', 'gIds', 'dIds','disabled','index'],
  data() {
      return {
        distributorList: [],
        distributors:[],
        formData: {
          distributorType: 0,
          distributorGradeIds: [],
          distributorIds: [],
          distributorData: [],
        },
        pageInfo: {
          page: 1,
          count: 10000
        },
        distributorShow: false,
        multipleSelection: []
      }
  }, 
  components: { selectDistributor },
  created() {
    this.formData.distributorData = []
    this.formData.distributorType = this.type;
    this.formData.distributorGradeIds = this.gIds;
    this.formData.distributorIds = this.dIds;
    if(this.formData.distributorIds !== undefined && this.formData.distributorIds !== null && this.formData.distributorIds.length>0){
      if(this.distributors === undefined || this.distributors.length === 0){
        this.$http.getDistributorPoList(this, { page:1, size:10000,freezeStatus: 1,profileStatus: 2}).then(res => {			    
          this.distributors = res.data.list
          this.$store.commit('GET_DISTRIBUTORS', res.data.list)
          this.initDistributor()
        })
      }else{
        this.initDistributor()
      }
    }
    this.$http.getGradePoList(this, { page:1, size:10000, openFlag: 1 }).then(res => {
      if(res.success) {
        this.distributorList = res.data.list
      }
    })
  },
  methods: {
    handleDeleteDistributor(index) {
      this.formData.distributorData.splice(index, 1)
      // this.$refs.selectDistributor.selectRow()
    },
    initDistributor(){
      this.formData.distributorIds.forEach(item => {
        this.distributors.forEach(val => {
          if(val.id == Number(item)) {
            this.formData.distributorData.push(val)
          }
        })
      })
      this.formData.distributorData = this.setArr(this.formData.distributorData)
    },
    add() {
      this.$emit('add')
    },
    disCancel() {
      this.$refs.selectDistributor.handleCancel()
    },
    cancel(){
      this.distributorShow = false
    },
    disSubmit(msg) {
      this.formData.distributorData = msg;
      this.distributorShow = false;
    },
      
  setArr(arr){ //去重
    let obj ={};  
    let temp=[];  
    for( let i = 0; i < arr.length; i++ ) {  
      let type= Object.prototype.toString.call(arr[i].id);//不加类型 分不清 1 '1'    
        if( !obj[ arr[i].id +type] ) {  
          temp.push( arr[i] );  
          obj[ arr[i].id+ type ] =true;//这里给true 利于代码阅读和判断。  如果给 0,'' ,false ,undefined 都会在if那里判断为 false 不利于代码阅读  
        }
      } 
    return temp; 
  },
  getParams(type,gIds,dIds){
    this.formData.distributorData = []
    this.formData.distributorType = type;
    this.formData.distributorGradeIds = gIds;
    this.formData.distributorIds = dIds;
    if(this.formData.distributorIds !== undefined && this.formData.distributorIds !== null && this.formData.distributorIds.length>0){
      if(this.distributors === undefined || this.distributors.length === 0){
        this.$http.getDistributorPoList(this, { page:1, size:10000,freezeStatus: 1,profileStatus: 2}).then(res => {			  
          this.distributors = res.data.list
          this.$store.commit('GET_DISTRIBUTORS', res.data.list)
          this.initDistributor()
        })
      }else{
        this.initDistributor()
      }
    }
    this.$http.getGradePoList(this, { page:1, size:10000, openFlag: 1 }).then(res => {  
      if(res.success) {
        this.distributorList = res.data.list
      }
    })
  },
  },
  watch: {
    formData: {
      handler() {
        this.$emit('change', this.formData,this.index)
      },
      deep: true
    },
    dIds(val) {
      this.formData.distributorIds = val instanceof Array ? val : val == undefined ? [] : val.splitnum(',')
      this.initDistributor()
    },
    gIds(val) {
      this.formData.distributorGradeIds = val instanceof Array ? val : val === undefined ? [] : val.splitnum(',')
    },
    type(val) {
      this.formData.distributorType = val
    },
  }
}
</script>

<style lang="scss" scoped>
.el-radio-group .el-checkbox:first-child {
    margin-left: 30px;
}

.dis-item2 {
  margin-top: 16px;
}
.dis-item1 {
  margin-top: 8px;
}
.dis-item3 {
  margin-top: 16px;
}

.distributor-content button {
  margin: 10px 0;
}

</style>
