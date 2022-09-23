function registElem() {
    return {
        itemInfoDto: {
            itemName: '',
            itemCode: '',
            id: '',
        },
        issue: '',
        createdAt: '',
        searchItemNameAndCode: '',
        showSearchListItem: false,
        focusedSearchItemIndex: null,
        searchedItemList: [],
        id: document.getElementById('id').value,
        async init() {
            if(this.id != ''){
                await this.getById(this.id);
            }
        },
        getSearchItemList(param) {
            apiFetchJson('/item/findByItemNameContainingOrItemCodeContaining', 'post', JSON.stringify(param))
                .then(res => res.json())
                .then(data => {
                    if (data)
                        this.searchedItemList = data;
                })
        },
        eventKeyupInSearchItem(e){
            if ([13, 37, 38, 39, 40].includes(e.keyCode)) return;
            if (!this.showSearchListItem) this.showSearchListItem = true;
            this.focusedSearchItemIndex = null;

            let searchItemNameAndCode = this.searchItemNameAndCode;
            if (!searchItemNameAndCode) return;

            param = {"searchItemNameAndCode" : searchItemNameAndCode};
            this.getSearchItemList(param);
        },
        eventArrowUpInSearchItemList() {
            if (this.focusedSearchItemIndex === null) return this.focusedSearchItemIndex = this.searchedItemList.length - 1;
            if (this.focusedSearchItemIndex <= 0) return;
            this.focusedSearchItemIndex--;
        },
        eventArrowDownInSearchItemList() {
            if (this.focusedSearchItemIndex === null) return this.focusedSearchItemIndex = 0;
            if (this.focusedSearchItemIndex + 1 >= this.searchedItemList.length) return;
            this.focusedSearchItemIndex++;
        },
        eventEscapeInSearchItemList() {
            if(this.itemInfoDto.id == '' || this.getItemInfoName(this.itemInfoDto.itemName, this.itemInfoDto.itemCode) != this.searchItemNameAndCode) {
                this.searchItemNameAndCode = '';
                this.itemInfoDto.itemName = '';
                this.itemInfoDto.itemCode = '';
                this.itemInfoDto.id = '';
            }

            if (this.showSearchListItem) {
                this.showSearchListItem = false;
                this.focusedSearchItemIndex = null;
                this.searchedItemList = [];
            }
        },
        eventEnterInSearchItemList(index) {
            if (index != null &&  index > 0) this.focusedSearchItemIndex = index;
            if (!this.showSearchListItem) return this.showSearchListItem = true;
            let selectedItem = this.searchedItemList[this.focusedSearchItemIndex];

            this.itemInfoDto = selectedItem;
            this.searchItemNameAndCode = this.getItemInfoName(selectedItem.itemName, selectedItem.itemCode);
            this.eventEscapeInSearchItemList();
        },
        saveBoard() {
            const param = {};
            param.issue = this.issue;
            param.itemInfoDto = this.itemInfoDto;
            param.id = this.id;
            param.createdAt = this.createdAt;

            if(param.itemInfoDto == null || param.itemInfoDto.id == ''){
                alert('매핑된 코드가 없습니다');
                return;
            }

            apiFetchJson('/board/save', 'post', JSON.stringify(param))
                .then(res => res.json())
                .then(data => {
                    if (data && data.id != ''){
                        alert('저장되었습니다');
                        location.href = "/";
                    }
                })
        },
        async getById(id){
            const param = {};
            await apiFetchJson(`/board/getById/${id}`, 'get')
                .then(res => res.json())
                .then(data => {
                    if (data && data.id != ''){
                        this.itemInfoDto = data.itemInfoDto;
                        this.issue = data.issue;
                        this.createdAt = data.createdAt;
                        this.searchItemNameAndCode = this.getItemInfoName(data.itemInfoDto.itemName, data.itemInfoDto.itemCode);
                    }
                })
        },
        getItemInfoName(name, code){
            return name + ' - ' + code;
        }
    }
}


