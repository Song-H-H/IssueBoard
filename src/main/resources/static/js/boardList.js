function listElem() {
    return {
        boardList: [],
        searchItemInfoOrIssue : '',
        pageDto: {
            pageNumber : 0,
            totalPages : 0,
        },
        async init() {
            await this.getSearchList();
        },
        async getSearchList() {
            const param = {};
            param.searchItemInfoOrIssue = this.searchItemInfoOrIssue;
            param.pageDto = this.pageDto;

            await apiFetchJson('/board/findByItemInfoOrIssueContaining', 'post', JSON.stringify(param))
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    if (data)
                        this.boardList = data;
                        this.pageDto = data[0].pageDto;
                })
        },
        deleteBoard(id){
            if(!confirm("delete?")){
                return;
            }
            const param = {'id':id};
            apiFetchJson('/board/deleteById', 'post', JSON.stringify(param))
                .then(data => {
                    if (data){
                        this.getSearchList();
                    }
                })
        },
        goPage(pageNumber){
            if(pageNumber > this.pageDto.totalPages-1) pageNumber = this.pageDto.totalPages-1;
            if(pageNumber < 0) pageNumber = 0;

            this.pageDto.pageNumber = pageNumber;
            this.getSearchList();
        },
        exportExcel(){
            const param = {};
            param.searchItemInfoOrIssue = this.searchItemInfoOrIssue;

            apiFetchExcel(
                '/board/exportExcel',
                'post',
                JSON.stringify(param),
                `IssueBoard_${new Date().getTime()}.xlsx`
            ).then();
        }
    }
}


