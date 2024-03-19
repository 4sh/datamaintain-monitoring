package execution.batch

enum class Origin {
    CLIENT, // From UI
    SERVER, // From Datamaintain
    TIER // From something else than anything Datamaintain related
}