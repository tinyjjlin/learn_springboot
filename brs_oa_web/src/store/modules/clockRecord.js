// 实时打卡记录
const clockRecord = {
    state:{
        clockUpdateStatus:false,
    },
    mutations:{
        CHANGE_CLOCK_UPDATE_STATUS:(state) =>{
            state.clockUpdateStatus = !state.clockUpdateStatus; 
        }
    },
    actions:{

    }
}

export default clockRecord