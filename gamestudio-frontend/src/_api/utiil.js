export const formatDate = (date) => {
    let dateString = date.toString();
    dateString = dateString.slice(0,dateString.length.lenght - 1);
    return dateString;
}