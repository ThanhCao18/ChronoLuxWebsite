
/**
 * Format number to Vietnamese currency format
 * @param {number} amount - The amount to format
 * @returns {string} - Formatted currency in VND
 */
function formatToVND(amount) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
}
