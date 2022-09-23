function listElem() {
    return {
        searchItemNameAndCode: '',
        itemInfo: [],
        async init() {
            await this.getSearchItemList();
        },
        async getSearchItemList() {
            const param = {'searchItemNameAndCode':this.searchItemNameAndCode};
            await apiFetchJson('/item/findByItemNameContainingOrItemCodeContaining', 'post', JSON.stringify(param))
                .then(res => res.json())
                .then(data => {
                    if (data)
                        this.itemInfo = data;
                })
        },
        addNewLine(){
            let itemInfo = {
                id : '',
                itemName : '',
                itemCode : '',
            }
            this.itemInfo.unshift(itemInfo);
        },
        saveItemInfo(itemInfo){
            if(!confirm('Save Item Info?')){
                return;
            }

            apiFetchJson('/item/save', 'post', JSON.stringify(itemInfo))
                .then(res => res.json())
                .then(data => {
                    if (data){
                        itemInfo.id = data;
                    }
                })
        }
    }
}


