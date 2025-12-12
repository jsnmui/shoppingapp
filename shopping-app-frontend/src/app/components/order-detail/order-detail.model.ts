export interface OrderItem {
  itemId: number;
  userId: number;
  username: string;
  quantity: number;
  purchasedPrice: number;
  productId: number;
  productName: string;
}

export interface OrderDetail {
  orderId: number;
  datePlaced: string;
  orderStatus: string;
  items: OrderItem[];
}